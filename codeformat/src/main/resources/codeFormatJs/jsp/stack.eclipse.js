/**
 * 栈
 */
var Stack = function() {
	var cache = [];
	// 进栈
	this.put = function() {
		arguments.length ? Array.prototype.push.apply(cache, Array.prototype.slice.call(arguments)) : '';
	}
	// 出栈
	this.get = function() {
		return cache.pop();
	}
	// 最后一项
	this.last = function() {
		return this.empty() ? null : cache[this.size() - 1];
	}
	// 栈长度
	this.size = function() {
		return cache.length;
	}
	// 栈是否为空
	this.empty = function() {
		return this.size() === 0;
	}
	// 栈清空
	this.clear = function() {
		cache.length = 0;
	}
	// 栈快照
	this.copy = function() {
		return cache.slice();
	}
};
