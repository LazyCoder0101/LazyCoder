
/**
 * 节点对象(extend-tag)
 */
var ETag = function(name, header, body, footer, parent) {
	this._name = name;
	this._header = header;
	this._body = body;
	this._footer = footer;
	this._parent = parent;
};

/**
 * get,set方法底层
 */
ETag.prototype._getset = function(prop, value) {
	if (value !== undefined) {
		this[prop] = value;
	}
	return this[prop];
};

/**
 * 节点名称
 * 
 * @param {String}
 *            name
 */
ETag.prototype.name = function(name) {
	return this._getset('_name', name);
};

/**
 * 节点开始部分
 * 
 * @param {String}
 *            header
 */
ETag.prototype.header = function(header) {
	return this._getset('_header', header);
};

/**
 * 节点内容
 * 
 * @param {Object}
 *            body
 */
ETag.prototype.body = function(body) {
	return this._getset('_body', body);
};

/**
 * 节点结束部分
 * 
 * @param {String}
 *            footer
 */
ETag.prototype.footer = function(footer) {
	return this._getset('_footer', footer);
};

/**
 * 父节点
 * 
 * @param {String}
 *            parent
 */
ETag.prototype.parent = function(parent) {
	return this._getset('_parent', parent);
};

/*
 * 设置html单标签
 */
ETag.prototype.singleTags = [ '!doctype', '!--', 'area', 'base', 'basefont', 'br', 'col', 'embed', 'frame', 'hr',
		'img', 'input', 'keygen', 'link', 'meta', 'param', 'source', 'track', 'wbr', '_text_', '%--', '%@' ];

/**
 * 是否是单标签
 */
ETag.prototype.singleTag = function() {
	// 性能不好
	// return (','+this.singleTags.join(',')+',').indexOf(','+this.name+',')>-1;
	if (!this.header() && !this.footer() && /[%\/]>$/.test(this.body())) {
		return true;
	}
	return this.singleTags.some(function(el) {
		return el === this.name();
	}, this);
};

/**
 * 是否是空body元素，例如script或者空的p
 */
ETag.prototype.emptyBodyTag = function() {
	return this.header() && this.footer() && (this.isComplexTag() ? this.body().length === 0 : !this.body());
}

/**
 * 是否是脚本
 */
ETag.prototype.isScript = function() {
	return this.name() === 'script';
};

/**
 * 是否是样式
 */
ETag.prototype.isCSS = function() {
	var name = this.name();
	return name === 'style'
			|| (name === 'link' && name.search(/rel\s*=\s*("|')?stylesheet\1|type\s*=\s*("|')?text\/css\1/) > -1);
};

/**
 * 标签长度
 */
ETag.prototype.size = function() {
	if (this._size != undefined) {
		return this._size;
	}
	var header = this.header() || '', body = this.body() || '', footer = this.footer() || '';
	this._size = header.length;
	if (body) {
		if (this.isComplexTag()) {
			if (body instanceof ETag) {
				this._size += body.size();
			} else {
				body.forEach(function(el) {
					this._size += el.size();
				}, this);
			}
		} else {
			this._size += body.length;
		}
	}
	this._size += footer.length;
	return this._size;
};

/**
 * 是否是嵌套的标签
 */
ETag.prototype.isComplexTag = function() {
	var tag = this.body();
	return tag && (Object.prototype.toString.call(tag).slice(8, -1) === 'Array' || tag instanceof ETag);
};
