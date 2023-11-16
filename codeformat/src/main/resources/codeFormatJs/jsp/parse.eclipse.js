/**
 * 格式化工具
 * 
 * @param {String}
 *            sourceText
 */
var Parse = function(sourceText) {
	this.fileText = sourceText;
};

/**
 * 初始化方法
 * 
 * @return {Boolean}--初始化标示
 */
Parse.prototype.init = function(opt) {
	this.index = -1;// 当前指针位置
	this.temp = '';// 临时存储文件截取内容
	this.static = {
		gt : '>',
		lt : '<',
		r : '\r',
		n : '\n',
		sq : '\'',
		dq : '"'
	};
	// 特殊的标签对，正常的是<span></span>或<img />
	this.tagpair = {
		'!--' : '-->',
		'%--' : '--%>'
	};
	// 实体名称
	this.entity = {
		'{' : 'lcb',// left curly braces
		'}' : 'rcb',
		':' : 'colon',
		'%' : 'percent',
		'/' : 'slash',
		'\\' : 'backslash'
	}
	if (this.fileText != null) {
		this.fileLength = this.fileText.length;// 文件内容长度
	}
	this.tag = new ETag(null, null, [], null);
	this.parentTag = this.tag;
	this.deeps = 1;
	this.stack = new Stack();
	if (opt) {
		for ( var i in opt) {
			this[i] = opt[i];
		}
	}
	return this.fileText != null;
};

/**
 * 格式化方法
 * 
 * @return {Object}--文件内容
 */
Parse.prototype.parse = function(opt) {
	return this.init(opt) && this.role();
};

/**
 * 遍历文本内容
 */
Parse.prototype.role = function() {
	if (this.noFormat) {
		return this.fileText;
	}
	if (this.fileLength) {
		while (this.fileLength > this.index + 1) {
			this.getNextBody();
			if (!this.empty()) {
				var tag = new ETag('_text_', null, this.extTrim(), null, this.parentTag);
				this.parentTag.body().push(tag);
			}
			this.getNextTag();
			if (this.singleTag()) {
				var tag = new ETag(this.tempName(), null, this.extTrim(), null, this.parentTag);
				this.parentTag.body().push(tag);
			} else if (this.endTag()) {
				this.parentTag.footer(this.extTrim());
				this.parentTag = this.parentTag.parent();
				this.deeps--;
			} else {
				var tagName = this.tempName();
				var tag = new ETag(tagName, this.extTrim(), [], null, this.parentTag);
				this.parentTag.body().push(tag);
				this.parentTag = tag;
				this.deeps++;
				tagName = this.entityName('format' + tagName);
				if (tagName in this) {
					this[tagName]();
				}
			}
		}
	}
	return this.tag;
}

/**
 * 开始标签和结束标签是否配对
 */
Parse.prototype.pair = function(left, right) {
	if (left && right) {
		var reg = /[<>\/]+/g;
		left = left.replace(reg, '');
		right = right.replace(reg, '');
		return left === right;
	}
}

/**
 * 检查是否是合适的body
 */
Parse.prototype.getNextBody = function() {
	this.temp = this.nextLetterUntil(this.static.lt);
	if (!this.trim(this.temp)) {
		return;
	}
	var temp = this.temp, index;
	// body 中出现EL表达式例如${a < b}
	while ((index = this.temp.lastIndexOf('${')) > -1 && index > this.temp.lastIndexOf('}')) {
		this.temp = this.nextLetter('}');
		if (this.temp) {
			temp += this.temp;
			this.temp = this.nextLetterUntil(this.static.lt);
//		} else {
		}
	}
	this.temp = temp;
}

/**
 * 获得下一个标签
 */
Parse.prototype.getNextTag = function() {
	// 获得一对尖括号，它可能是一个标签
	this.temp = this.nextLetter(this.static.lt) + this.nextLetter(this.static.gt);
	if (!this.trim(this.temp)) {
		return;
	}
	// 处理标签是注释的情况
	var tagName = this.tempName(), tagpair = '';
	if ((tagpair = this.tagpair[tagName])) {
		if (this.temp.substr(-tagpair.length) !== tagpair) {
			this.temp += this.nextLetter(tagpair);
		}
		return;
	}

	var temp = this.temp, index = this.index;
	// <c:if,</c:if>,${,},/>,>,',"
	var jstlReg = /<\/?(?:[a-zA-Z]+:[a-zA-Z]+>?)|(?:\$\{)|(?:\})|(?:\/?>)|(?:')|(?:")/;
	var result = this.regExec(jstlReg, temp.substr(1)), jstlTag;
	if (!result) {
		return;
	}
	tagName = this.tempName();
	if (!this.trim(tagName)) {
		return;
	}
	// 用栈去处理标签内成对的el或jstl或'或"
	this.stack.clear();
	this.stack.put(tagName);
	jstlReg = new RegExp(jstlReg.source, 'g');
	while (!this.stack.empty()) {
		result = this.regExec(jstlReg, this.temp, tagName.indexOf(':') > -1 ? tagName.length : 0);
		tagName = '';
		if (!result) {
			this.temp = this.nextLetter(this.static.gt);
			temp += this.temp;
			index = this.index;
		} else {
			var flag = this._hitStackLast(result[0]);
			if (flag === true) {
				this.stack.get();
			} else if (flag === false) {
				this.stack.put(result[0])
			}
		}
	}
	this.temp = temp;
	this.index = index;
}

/**
 * 与栈最后一个元素配对，当前栈里面要不就是el要不就是jstl或者引号和一个标签
 */
Parse.prototype._hitStackLast = function(txt) {
	var last = this.stack.last() || '';
	if (txt.match(/\/[a-zA-Z]+:[a-zA-Z]+>/)) {
		if (txt.indexOf(':') > -1 && last.indexOf(':') > -1
				&& this.trim(txt).replace(/[<>\/]+/g, '').indexOf(this.trim(last).replace(/[<>\/]+/g, '')) > -1) {
			return true;
		}
		return null;
	}
	switch (last) {
	case '"':
		return txt === '>' ? null : txt === '"';
	case "'":
		return txt === '>' ? null : txt === "'";
	case '${':
		return txt === '>' ? null : txt === '}';
	default:
		var trimTxt = this.trim(txt), trimLast = this.trim(last);
		if (trimTxt.length > trimLast.length && (trimTxt.indexOf(':') > -1 || trimLast.indexOf(':') > -1)) {
			return trimTxt.indexOf(trimLast) > -1
					|| trimTxt.replace(/[<>\/]+/g, '').indexOf(trimLast.replace(/[<>\/]+/, '')) > -1;
		}
		return txt.search(/\/?>/) > -1;
		/*
		 * if (last.indexOf(':') > -1) { return txt === '>' ? null :
		 * (txt.indexOf(':') > -1 || txt === '/>'); } else { return
		 * txt.search(/\/?>/) > -1; }
		 */
	}
}

/**
 * 单行的标签
 */
Parse.prototype.singleTag = function() {
	if (this.temp) {
		if (/[%\/]>$/.test(this.temp)) {
			return true;
		}
		var tagName;
		return (tagName = this.tempName()) && this.tag.singleTags.some(function(el) {
			return el === tagName;
		});
	}
	return false;
};

/**
 * 获得当前截取文本的标签名
 */
Parse.prototype.tempName = function() {
	if (this.temp) {
		// <!--[if lt IE 9]>,<!--hh-->,<img/>
		var result = this.regExec(/<\/?([%!]--\[?|[^ \t\r\n></]+|[a-zA-Z]+:[a-zA-Z]+)/, this.temp);
		if (result && result.length === 2) {
			return result[1].toLowerCase();
		}
	}
	return '';
}

/**
 * 执行正则
 */
Parse.prototype.regExec = function(reg, txt, indexFrom) {
	/*
	 * var result, temp, beginFromLast = !!beginFromLast; try { result =
	 * reg.exec(txt); while (beginFromLast && (temp = reg.exec(txt))) { result =
	 * temp; } } catch (e) { }
	 */
	var result;
	if (indexFrom) {
		txt = new Array(indexFrom + 1).join('-') + txt.substr(indexFrom);
	}
	try {
		result = reg.exec(txt);
	} catch (e) {
	}
	return result;
}

/**
 * 结束标签
 */
Parse.prototype.endTag = function() {
	if (this.temp) {
		var tagStr = this.temp.trim();
		return tagStr.indexOf('</') === 0 || /--[!%]?>$/.test(tagStr);
	}
	return false;
}

/**
 * 标签名称转换为实体名称
 */
Parse.prototype.entityName = function(name) {
	return (name || '').split('').map(function(a) {
		return this.entity[a] || a;
	}, this).join('');
}

/**
 * 解析style，让style每条一行
 */
Parse.prototype.formatstyle = function() {
	if (this.parentTag.name() !== 'style') {
		return;
	}
	this.temp = this.nextLetterUntil('</style');
	if (!this.empty()) {
		var styleBodyArr = this.extTrim().replace(/} /g, '}:@:#%*:').split(':@:#%*:');
		styleBodyArr.forEach(function(el) {
			this.parentTag.body().push(new ETag('_text_', null, el, null, this.parentTag));
		}, this);
	}
}

/**
 * 解析script，原样不变
 */
Parse.prototype.formatfocuscolonstatic = function() {
	if (this.parentTag.name() !== 'focus:static' || this.parentTag.header().indexOf('text/javascript') < 0) {
		return;
	}
	// 获得<script>标签前面的空格或者tab数量，然后相当于把整个<script></script>包括之内的内容移动到最左边。这是为了格式化后<script>标签和内容相对位置保持一致。
	var txt = this.fileText.substring(0, this.index + 1).replace(/<focus:static[^>]*>/g, '') || '';
	var result = this.regExec(/([ \t]+)$/, txt);
	if (result && result.length === 2) {
		txt = result[1];
	}
	var match;
	this.temp = this.nextLetterUntil('</focus:static');
	if (!this.empty()) {
		var styleBodyArr = this.temp.split(/\r\n/);
		styleBodyArr.forEach(function(el) {
			if (!this.empty(el)) {
				this.parentTag.body().push(new ETag('_text_', null, el.replace(txt, ''), null, this.parentTag));
			}
		}, this);
	}
}

/**
 * 解析script，原样不变
 */
Parse.prototype.formatscript = function() {
	if (this.parentTag.name() !== 'script') {
		return;
	}
	// 获得<script>标签前面的空格或者tab数量，然后相当于把整个<script></script>包括之内的内容移动到最左边。这是为了格式化后<script>标签和内容相对位置保持一致。
	var txt = this.fileText.substring(0, this.index + 1).replace(/<script[^>]*>/g, '') || '';
	var result = this.regExec(/([ \t]+)$/, txt);
	if (result && result.length === 2) {
		txt = result[1];
	}
	var match;
	this.temp = '';
	do {
		if (this.trim()) {
			this.temp += this.nextLetter('</script');
		}
		this.temp += this.nextLetterUntil('</script');
		match = this.trim().match(/<\/?script>?/g);
		// 脚本有script字符串，希望它是成对出现的，不然这里还是会出错.
	} while (match && match.length % 2 === 1);
	if (!this.empty()) {
		var styleBodyArr = this.temp.split(/\r\n/);
		styleBodyArr.forEach(function(el) {
			if (!this.empty(el)) {
				this.parentTag.body().push(new ETag('_text_', null, el.replace(txt, ''), null, this.parentTag));
			}
		}, this);
	}
}

/**
 * 取下一个字符
 */
Parse.prototype.nextLetter = function() {
	if (this.index < this.fileLength) {
		if (arguments.length) {
			var minIndex = Number.MAX_VALUE, index = -1, temp = '', minText = '';
			Array.prototype.slice.call(arguments).forEach(function(el, i) {
				index = this.fileText.indexOf(el, this.index + 1);
				if (index > -1 && index < minIndex) {
					minIndex = index;
					minText = el;
				}
			}, this);
			if (minIndex > -1 && minIndex < Number.MAX_VALUE) {
				temp = this.fileText.substring(this.index + 1, minIndex + minText.length);
				this.index = minIndex + minText.length - 1;
				return temp;
			}
		} else {
			return this.fileText.subStr(++this.index, 1);
		}
	}
	return '';
}

/**
 * 取下一个字符
 */
Parse.prototype.nextLetterUntil = function(letter) {
	if (this.index < this.fileLength) {
		if (letter !== undefined) {
			var index = this.fileText.indexOf(letter, this.index + 1);
			if (index === 0) {
				return '';
			} else if (index === -1) {
				index = this.fileLength;
			}
			var temp = this.fileText.substring(this.index + 1, index);
			this.index = index - 1;
			return temp;
		}
	}
	return '';
}

/**
 * 是否为空
 */
Parse.prototype.empty = function(txt) {
	var txt = (txt !== undefined ? txt : this.temp) || '';
	return txt.length < 1 || /^\s*$/.test(txt);
}

/**
 * 首尾去空去回车换行
 */
Parse.prototype.trim = function(txt) {
	var txt = (txt !== undefined ? txt : this.temp) || '';
	if (!this.empty(txt)) {
		return txt.replace(/^\s+/g, '').replace(/\s+$/g, '');
	}
	return '';
}

/**
 * 增强的去空-多个空格合并为一个，回车换行换成空格
 */
Parse.prototype.extTrim = function(txt) {
	var txt = txt !== undefined ? txt : this.temp;
	if (!this.empty(txt)) {
		if (/[\r\n]/.test(txt)) {
			var ext = ':&:#%@:';
			while (txt.indexOf(ext) > -1) {
				ext = ext + ext.substr(Math.floor(Math.random() * ext.length), 1);
			}
			// 回车换行多个空格都替换成一个空格
			txt = txt.replace(/\s+/g, ext).replace(new RegExp(ext, 'g'), ' ');
		}
		return this.trim(txt);
	}
	return '';
}


