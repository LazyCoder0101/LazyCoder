/**
 * 代码摘自 https://github.com/csekme/JRibbonMenu
 * Copyright 2020 Csekme Krisztián
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lazycoder.uiutils.ribbonmenu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Layer implements Iterable<com.lazycoder.uiutils.ribbonmenu.VirtualObject> {

		private List<com.lazycoder.uiutils.ribbonmenu.VirtualObject> objs;
		
		/**
		 * Create Layer for Visible Objects
		 */
		public Layer() {
			objs = new ArrayList<>();
		}
		
		/**
		 * Add object to the layer
		 * @param ob as VirtualObject instance
		 */
		public void add(com.lazycoder.uiutils.ribbonmenu.VirtualObject ob) {
			objs.add(ob);
		}
		
		/**
		 * Get the Virtual Object instance with number
		 * @param number integer as index
		 * @return VirtualObject instance
		 */
		public com.lazycoder.uiutils.ribbonmenu.VirtualObject get(int number) {
			return objs.get(number);
		}
		
		/**
		 * The number of objects that the layer contains
		 * @return the number of objet
		 */
		public int count() {
			return objs.size();
		}
		
		/**
		 * Clear hover flag on every objects
		 */
		public void clearHover() {
			this.forEach( o->{ o.setHover(false); } );
		}

		/**
		 * Collection of VirtualObject
		 */
		@Override
		public Iterator<com.lazycoder.uiutils.ribbonmenu.VirtualObject> iterator() {
			return objs.iterator();
		}
}
