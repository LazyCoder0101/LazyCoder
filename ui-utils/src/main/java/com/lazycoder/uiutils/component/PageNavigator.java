package com.lazycoder.uiutils.component;

import com.lazycoder.uiutils.mycomponent.MyButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 该源码摘自网上，已找不到原来出处 分页控件。
 *
 * <p>
 * 该控件包含分页所需显示的信息（如总记录数、当前页面、页面容量等），以及分页控制（第一页、上一页、下一页、最后页、页面跳转等）。
 * <p>
 * 一般可加入到面板的下方，对实现<code>Navigatable</code>接口的列表进行排序。
 * <p>
 *
 * @author sundw
 */
public class PageNavigator extends JPanel {
	/* serialVersionUID */
	private static final long serialVersionUID = -3686142720195694938L;

	// private Navigatable target = null; // 排序对象
	protected int totalCount = 0; // 总记录数
	protected int pageCount = 1; // 页面总数
	protected int currentPage = 1; // 当前页面
	protected int pageSize = 10; // 页面容量
	private MyButton buttonFirst = new MyButton(); // 第一页
	private MyButton buttonPrev = new MyButton(); // 上一页
	private MyButton buttonNext = new MyButton(); // 下一页
	private MyButton buttonLast = new MyButton(); // 最后页
	protected MyButton buttonGo = new MyButton(); // 跳转
	protected JComboBox comboBoxPageSize = new JComboBox(); // 每页容量
	private JTextField textFieldJumpTo = new TextFieldJumpTo(); // 跳转到
	private JLabel labelBeforeTotalCount = new JLabel();
	private JLabel labelTotalCount = new JLabel();
	private JLabel labelAfterTotalCount = new JLabel();
	private JLabel labelBeforePageCount = new JLabel();
	private JLabel labelPageCount = new JLabel();
	private JLabel labelAfterPageCount = new JLabel();
	private JLabel labelBeforeCurrentPage = new JLabel();
	private JLabel labelCurrentPage = new JLabel();
	private JLabel labelAfterCurrentPage = new JLabel();
	private JLabel labelBeforePageSize = new JLabel();
	private JLabel labelAfterPageSize = new JLabel();
	private JLabel labelBeforeJumpTo = new JLabel();
	private JLabel labelAfterJumpTo = new JLabel();
	private FlowLayout layout = new FlowLayout();

	/**
	 * 构造函数，需传入待控制的target
	 */
	public PageNavigator() {
//		init();
//		currentPage = 1;
//		go();
	}

//	public PageNavigator() {
//		init();
//	}

	protected void init() {
		initUI();
		initBehavior();
	}

	// 初始化UI
	private void initUI() {
		buttonFirst.setText("第一页(F)");
		buttonFirst.setMnemonic('F');

		buttonPrev.setText("上一页(P)");
		buttonPrev.setMnemonic('P');

		buttonNext.setText("下一页(N)");
		buttonNext.setMnemonic('N');
		buttonLast.setText("最后页(L)");
		buttonLast.setMnemonic('L');

		buttonGo.setText("跳转");

		labelBeforeTotalCount.setText("共有");
		labelAfterTotalCount.setText("条记录");
		labelBeforePageCount.setText("共有");
		labelAfterPageCount.setText("页");
		labelBeforeCurrentPage.setText("当前是第");
		labelAfterCurrentPage.setText("页");
		labelBeforePageSize.setText("每页");
		labelAfterPageSize.setText("条");
		labelBeforeJumpTo.setText("转到第");
		labelAfterJumpTo.setText("页");

		comboBoxPageSize.setModel(new DefaultComboBoxModel(new String[]{"2", "5", "10", "20", "50"}));
		textFieldJumpTo.setPreferredSize(new Dimension(40, 25));
		comboBoxPageSize.setSelectedIndex(2);

		layout.setAlignment(FlowLayout.LEFT);
		this.setLayout(layout);
		this.setPreferredSize(new Dimension(10, 40));

		// 将各元素加入界面
		this.add(labelBeforeTotalCount);
		this.add(labelTotalCount);
		this.add(labelAfterTotalCount);
		this.add(labelBeforePageCount);
		this.add(labelPageCount);
		this.add(labelAfterPageCount);
		this.add(labelBeforeCurrentPage);
		this.add(labelCurrentPage);
		this.add(labelAfterCurrentPage);
		this.add(buttonFirst);
		this.add(buttonPrev);
		this.add(buttonNext);
		this.add(buttonLast);
		this.add(labelBeforePageSize);
		this.add(comboBoxPageSize);
		this.add(labelAfterPageSize);
		this.add(labelBeforeJumpTo);
		this.add(textFieldJumpTo);
		this.add(labelAfterJumpTo);
		this.add(buttonGo);
	}


	// 初始化各部件的行为
	private void initBehavior() {
		buttonFirst.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonFirst_actionPerformed(e);
			}
		});
		buttonPrev.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonPrev_actionPerformed(e);
			}
		});

		buttonNext.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonNext_actionPerformed(e);
			}
		});

		buttonLast.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonLast_actionPerformed(e);
			}
		});

		buttonGo.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonGo_actionPerformed(e);
			}
		});

		// 添加跳页的键盘监听，如果输入的页数<=0或>总页数，则清空输入框
		textFieldJumpTo.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				validate();
			}

			@Override
			public void keyTyped(KeyEvent e) {
				validate();
			}

			private void validate() {
				if (!"".equals(textFieldJumpTo.getText())) {
					int page = Integer.parseInt(textFieldJumpTo.getText());
					if (page <= 0 || page > pageCount) {
						textFieldJumpTo.setText("");
					}
				}
			}
		});

		setPageSize(10);// default pagesize == 10
	}

	private void buttonLast_actionPerformed(ActionEvent e) {
		currentPage = pageCount;
		go();
	}

	private void buttonNext_actionPerformed(ActionEvent e) {
		currentPage++;
		go();
	}

	private void buttonPrev_actionPerformed(ActionEvent e) {
		currentPage--;
		go();
	}

	private void buttonFirst_actionPerformed(ActionEvent e) {
		currentPage = 1;
		go();
	}

	private void buttonGo_actionPerformed(ActionEvent e) {
		// 如果跳转输入框为空，则默认为1
		if ("".equals(textFieldJumpTo.getText())) {
			pageSize = Integer.valueOf((String) comboBoxPageSize.getSelectedItem()).intValue();
			currentPage = 1;
			go();
		} else {
			// 获取每页记录数
			pageSize = Integer.valueOf((String) comboBoxPageSize.getSelectedItem()).intValue();

			currentPage = Integer.valueOf(textFieldJumpTo.getText()).intValue();

			int newPageCount = ((totalCount - 1) / pageSize) + 1;

			// 如果跳转输入框中的页数大于计算后的新的页总数，则默认为1
			if (currentPage > newPageCount) {
				currentPage = 1;
			}
			go();
		}
	}

	/**
	 * 跳转页面
	 */
	protected synchronized void go() {
//		if (target == null) {
//			return;
//		}
		if (totalCount <= 0) {
			totalCount = 0;
			pageCount = 0;
			currentPage = 0;
			// diable all controlers
			setAllControler(false);
//			target.pageTo(0, 0);
		} else {
			// enable all controlers
			setAllControler(true);

			if (currentPage < 1) {
				currentPage = 1;
			}
			if (currentPage > pageCount) {
				currentPage = pageCount;
			}
			// 设置按钮

			if (pageCount <= 1) {
				buttonFirst.setEnabled(false);
				buttonPrev.setEnabled(false);
				buttonNext.setEnabled(false);
				buttonLast.setEnabled(false);

			} else {
				if (currentPage == 1) {
					buttonFirst.setEnabled(false);
					buttonPrev.setEnabled(false);
					buttonNext.setEnabled(true);
					buttonLast.setEnabled(true);
				} else if (currentPage == pageCount) {
					buttonFirst.setEnabled(true);
					buttonPrev.setEnabled(true);
					buttonNext.setEnabled(false);
					buttonLast.setEnabled(false);
				} else {
					buttonFirst.setEnabled(true);
					buttonPrev.setEnabled(true);
					buttonNext.setEnabled(true);
					buttonLast.setEnabled(true);
				}
			}
//			target.pageTo(currentPage, pageSize);
		}

		// 显示label
		labelTotalCount.setText(String.valueOf(totalCount));
		labelPageCount.setText(String.valueOf(pageCount));
		labelCurrentPage.setText(String.valueOf(currentPage));
		// 清空跳转输入框
		textFieldJumpTo.setText("");
		textFieldJumpTo.requestFocus();
	}

	private void setAllControler(boolean b) {
		buttonFirst.setEnabled(b);
		buttonPrev.setEnabled(b);
		buttonNext.setEnabled(b);
		buttonLast.setEnabled(b);
		buttonGo.setEnabled(b);
		comboBoxPageSize.setEnabled(b);
		textFieldJumpTo.setEnabled(b);
	}

	/**
	 * 设置初始化页面状态
	 */
	public void initPageStatus() {
		initPageStatus(1, Integer.valueOf((String) comboBoxPageSize.getSelectedItem()).intValue());
	}

	/**
	 * 设置初始化页面状态
	 *
	 * @param currentPage
	 * @param pageSize
	 */
	public void initPageStatus(int currentPage, int pageSize) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
//		go();
	}

	/**
	 * 更新数据
	 */
	public void refresh() {
		go();
	}

	public JButton getButtonFirst() {
		return buttonFirst;
	}

	public JButton getButtonGo() {
		return buttonGo;
	}

	public JButton getButtonLast() {
		return buttonLast;
	}

	public JButton getButtonNext() {
		return buttonNext;
	}

	public JButton getButtonPrev() {
		return buttonPrev;
	}

	public JComboBox getComboBoxPageSize() {
		return comboBoxPageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public JLabel getLabelAfterCurrentPage() {
		return labelAfterCurrentPage;
	}

	public JLabel getLabelAfterJumpTo() {
		return labelAfterJumpTo;
	}

	public JLabel getLabelAfterPageCount() {
		return labelAfterPageCount;
	}

	public JLabel getLabelAfterPageSize() {
		return labelAfterPageSize;
	}

	public JLabel getLabelAfterTotalCount() {
		return labelAfterTotalCount;
	}

	public JLabel getLabelBeforeCurrentPage() {
		return labelBeforeCurrentPage;
	}

	public JLabel getLabelBeforeJumpTo() {
		return labelBeforeJumpTo;
	}

	public JLabel getLabelBeforePageCount() {
		return labelBeforePageCount;
	}

	public JLabel getLabelBeforePageSize() {
		return labelBeforePageSize;
	}

	public JLabel getLabelBeforeTotalCount() {
		return labelBeforeTotalCount;
	}

	public JLabel getLabelCurrentPage() {
		return labelCurrentPage;
	}

	public JLabel getLabelPageCount() {
		return labelPageCount;
	}

	public JLabel getLabelTotalCount() {
		return labelTotalCount;
	}

	@Override
	public FlowLayout getLayout() {
		return layout;
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页容量
	 *
	 * @param newPageSize
	 */
	public void setPageSize(int newPageSize) {
		String newPageSizeStr = String.valueOf(newPageSize);
		for (int i = 0; i < comboBoxPageSize.getModel().getSize(); i++) {
			if (newPageSizeStr.equals(comboBoxPageSize.getModel().getElementAt(i))) {
				comboBoxPageSize.setSelectedIndex(i);
				pageSize = newPageSize;
				return;
			}
		}
	}

//	public Navigatable getTarget() {
//		return target;
//	}
//
//	public void setTarget(Navigatable target) {
//		this.target = target;
//	}

	public JTextField getTextFieldJumpTo() {
		return textFieldJumpTo;
	}

	public int getTotalCount() {
		return totalCount;
	}
}

/**
 * 只接受数字的跳转输入框
 *
 * @author sundw
 */
class TextFieldJumpTo extends JTextField {
	/* serialVersionUID */
	private static final long serialVersionUID = 8077431012317479681L;

	@Override
	protected Document createDefaultModel() {
		return new JumpToDocument();
	}

	protected class JumpToDocument extends PlainDocument {
		/* serialVersionUID */
		private static final long serialVersionUID = -6185645753358502126L;

		@Override
		public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
			char[] insertChars = str.toCharArray();
			for (int i = 0; i < insertChars.length; i++) {
				if (!(Character.isDigit(insertChars[i]))) {
					return;
				}
			}
			super.insertString(offset, str, a);
		}
	}

}
