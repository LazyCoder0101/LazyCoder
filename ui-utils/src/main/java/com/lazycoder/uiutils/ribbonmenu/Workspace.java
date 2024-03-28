package com.lazycoder.uiutils.ribbonmenu;
import java.util.ArrayList;
import java.util.List;

//代码摘自 https://github.com/csekme/JRibbonMenu
public class Workspace {

		List<Layer> layers;
		
		public Workspace() {
			layers = new  ArrayList<>();
		}
		
		public Layer addLayer() {
			Layer layer = new Layer();
			layers.add(layer);
			return layer;
		}
}
