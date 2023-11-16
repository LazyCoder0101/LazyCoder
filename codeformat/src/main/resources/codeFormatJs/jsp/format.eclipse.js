
function jspFormat(sourceText) { //格式化代码
	var parse=new Parse(sourceText),
	opt={
		// 缩进字符
		tabStr : '  ',
		// 是否调试
		debug : true,
		// 单行最小宽度
		lineMinWidth : 80,
		noFormat : false
	};
	var txt = new Format(opt).format(parse.parse(opt));
    return txt;
}

/**
 * 格式化工具
 * 
 * @param {Object}
 *            filePath
 */
var Format = function(opt) {
	this.init(opt);
	// 内部使用变量
	this._formatedContent = '';
	this._tabsArr = [];
	// 是否需要显示为一行
	this._showAsLine = false;
}

/**
 * 初始化
 */
Format.prototype.init = function(opt) {
	this.tabStr = '  ';// 缩进字符
	this.lineMinWidth = 80;// 单行最小宽度。为了解决像<a href=''>a</a>这种很短的标签，但是会被格式成三行的情况。
	this.debug = false;
	if (opt) {
		for ( var i in opt) {
			this[i] = opt[i];
		}
	}
	// 标签转换成小写的
	this.lineMinWidthedTags=this.lineMinWidthedTags.map(function(a){return a.toLowerCase()});
}

/**
 * 单行最小宽度对应的标签
 */
Format.prototype.lineMinWidthedTags = [ 'a', 'p', 'div', 'span', 'del', 'small', 'option', 'ol', 'ul', 'li', 'label',
		'em', 'th', 'tr', 'td', 'dfn', 'dl', 'dt', 'dd', 'button', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'h7', 'strong',
		'title', 'c:when', 'c:otherwise', 'c:if', 'c:forEach', 'jsp:include', 'c:import', 'c:out', 'c:if',
		'focus:static', 'layout:override' ];

/**
 * 格式化方法
 * 
 * @return {String}--文件内容
 */
Format.prototype.format = function(etag, firstChild) {
	// 为了便于让日志更清晰，所以重新个_format
	if (this.noFormat) {
		this._formatedContent = etag;
	} else {
		this._format(etag, firstChild);
	}
	return this._formatedContent;
};

/**
 * 内部格式化方法
 */
Format.prototype._format = function(etag, firstChild) {
	var etag, length, body;
	if (!etag) {
		return this._formatedContent;
	}
	if (etag instanceof Array) {
		etag.forEach(function(el, i) {
			this._format(el, i === 0);// 第一个兄弟节点需要缩进，其它的不用
		}, this);
	} else {
		body = etag.body();
		var isParentFlat = this.checkFlatStatus(etag);
		if (this._formatedContent) {// 根节点不需要缩进
			this.newline(!isParentFlat);
			if (firstChild) {
				this.tabs(false, !isParentFlat);
			}
		}
		if (etag.singleTag()) {// 单标签直接取节点内容部分
			this.append(body, isParentFlat ? false : true);
		} else if (etag.emptyBodyTag()) {
			this.append(etag.header() + etag.footer(), isParentFlat ? false : true);
		} else {
			// 标签头
			if (etag.header()) {
				this.append(etag.header(), isParentFlat ? false : true);
			}
			// 标签主体
			if (this.isText(body) && body.length) {
				this.newline();
				this.tabs();
				this.append(body);
			} else if (etag.isComplexTag()) {
				this._format(body);
			} else {
				throw new Error('unknowed etag\'s body');
			}
			// 上面的etag.isComplexTag()后重新进入_format后，计算的_showAsLine被etag.footer()后面的this._showAsLine
			// = false修改了。
			// 标签尾
			if (etag.footer()) {
				this.checkFlatStatus(etag);
				this.newline();
				this.tabs(true);
				this.append(etag.footer());
			}
		}
	}
	this._showAsLine = false;
};

/**
 * 重新检查是否需要一行显示
 */
Format.prototype.checkFlatStatus = function(etag) {
	var tagName = etag.name(), isParentFlat = false;
	var tagNeedFlat = this.lineMinWidthedTags.some(function(val) {
		return val === tagName;
	});
	if (etag.size() < this.lineMinWidth && tagNeedFlat) {
		this._showAsLine = true;
		if (etag.parent() && etag.parent().size() < this.lineMinWidth) {
			tagName = etag.parent().name();
			tagNeedFlat = this.lineMinWidthedTags.some(function(val) {
				return val === tagName;
			});
			isParentFlat = tagNeedFlat;
		}
	} else {
		this._showAsLine = false;
		var parent = etag.parent(), tagName;
		while (parent && parent.size() < this.lineMinWidth) {
			tagName = parent.name();
			tagNeedFlat = this.lineMinWidthedTags.some(function(val) {
				return val === tagName;
			});
			// TODO
			// 可能会出现一个可以一行显示的元素包含一个未定义是否可以一行显示的元素也被一行显示，例如div下面的script
			// 如果要修改，这里要对直接对this._showAsLine=tagNeedFlat
			if (tagNeedFlat) {
				this._showAsLine = true;
				isParentFlat = true;
				break;
			}
			parent = parent.parent();
		}
	}
	return isParentFlat;
}

/**
 * 换行字符串
 */
Format.prototype.newline = function(force) {
	if (this._formatedContent && (!this._showAsLine || force)) {
		this._formatedContent += '\r\n';
	}
}

/**
 * 缩进
 */
Format.prototype.tabs = function(shrink, force) {
	if (!this._showAsLine || force) {
		!!shrink ? this._tabsArr.pop() : this._tabsArr.push(this.tabStr);
	}
}

/**
 * 是否是文本
 */
Format.prototype.isText = function(tag) {
	return Object.prototype.toString.call(tag).slice(8, -1) == 'String';
}

/**
 * 追加
 */
Format.prototype.append = function(txt, force) {
	if (force || !this._showAsLine) {
		this._formatedContent += this._tabsArr.join('');
//	} else {
	}
	this._formatedContent += txt;
}

