package lifegame;

//导入swing库
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

//创建一个类，继承自JFrame，表示窗口
public class LifeGame extends JFrame {

//定义方格子世界的行数和列数
	private static final int rows = 200;
	private static final int cols = 200;

//定义方格子世界的二维数组
	private int[][] world = new int[rows][cols];

//定义每个方格子的大小
	private static final int cellSize = 4;

//定义窗口的宽度和高度
	private static final int width = cols * cellSize;
	private static final int height = rows * cellSize;

//创建一个构造方法，初始化窗口和方格子世界
	public LifeGame() {
		// 调用父类的构造方法，设置窗口的标题
		super("生命游戏");

		// 设置窗口的大小和位置
		setSize(width, height);
		setLocationRelativeTo(null);

		// 设置窗口关闭时退出程序
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 设置窗口不可调整大小
		setResizable(false);

		// 初始化方格子世界，随机给每个细胞赋值0或1
		Random random = new Random();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				world[i][j] = random.nextInt(2);
			}
		}

		// 创建一个画布对象，用来绘制方格子世界
		Canvas canvas = new Canvas();

		// 将画布添加到窗口中，并使之可见
		add(canvas);
		setVisible(true);

		// 创建一个定时器对象，用来定时更新方格子世界并重绘画布
		Timer timer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 每隔100毫秒，调用update方法更新方格子世界，并重绘画布
				update();
				canvas.repaint();
			}
		});

		// 启动定时器
		timer.start();

	}
	
	

	// 定义一个方法，根据当前状态更新下一时刻的状态

	public void update() {
		// 创建一个新的二维数组，用来存储下一时刻的状态
		int[][] nextWorld = new int[rows][cols];

		// 遍历每个细胞，根据规则计算下一时刻的状态

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				// 获取当前细胞的状态和周围八个细胞的存活数
				int state = world[i][j];
				int liveNeighbors = countLiveNeighbors(i, j);

				// 根据规则更新下一时刻的状态
				if (state == 0 && liveNeighbors == 3) {
					nextWorld[i][j] = 1;
				} else if (state == 1 && (liveNeighbors == 2 || liveNeighbors == 3)) {
					nextWorld[i][j] = 1;
				} else {
					nextWorld[i][j] = 0;
				}
			}
		}
		// 将新的二维数组赋值给原来的二维数组，完成更新
		world = nextWorld;
	}

	// 定义一个方法，计算某个细胞周围八个细胞的存活数
	public int countLiveNeighbors(int row, int col) {
		// 定义一个变量，用来记录存活数
		int liveCount = 0;

		// 遍历周围八个细胞，如果是存活状态，就增加存活数
		for (int i = row - 1; i <= row + 1; i++) {
			for (int j = col - 1; j <= col + 1; j++) {
				// 跳过自身和越界的情况
				if (i == row && j == col)
					continue;
				if (i < 0 || i >= rows || j < 0 || j >= cols)
					continue;

				// 获取周围细胞的状态
				int state = world[i][j];

				// 如果是存活状态，就增加存活数
				if (state == 1) {
					liveCount++;
				}
			}
		}

		// 返回存活数
		return liveCount;
	}

	// 创建一个内部类，继承自Canvas，表示画布
	class Canvas extends JPanel {

		// 重写paint方法，在画布上绘制方格子世界的每个细胞
		@Override
		public void paint(Graphics g) {
			super.paint(g);

			// 遍历每个细胞，根据状态设置颜色，并填充矩形
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					// 获取当前细胞的状态
					int state = world[i][j];

					// 根据状态设置颜色，死亡为白色，存活为黑色
					if (state == 0) {
						g.setColor(Color.WHITE);
					} else {
						g.setColor(Color.BLACK);
					}

					// 计算当前细胞在画布上的位置和大小，并填充矩形
					int x = j * cellSize;
					int y = i * cellSize;
					g.fillRect(x, y, cellSize, cellSize);
				}
			}

			// 在画布上画出方格子的边框线，颜色为灰色
			g.setColor(Color.GRAY);
			for (int i = 0; i <= rows; i++) {
				int y = i * cellSize;
				g.drawLine(0, y, width, y);
			}
			for (int j = 0; j <= cols; j++) {
				int x = j * cellSize;
				g.drawLine(x, 0, x, height);
			}
		}
	}
}