import javax.swing.JFrame;

public class GameFrame extends JFrame{
	
	GameFrame() {
		this.add(new GamePanel());
		this.setTitle("Game Rắn Săn Mồi");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);  // CỬA SỔ SẼ KHÔNG THỂ THAY ĐỔI KÍCH THƯỚC BỞI NGƯỜI DÙNG
		this.pack();// TỰ ĐỘNG THAY ĐỔI KÍCH THƯỚC JFRAME SAO CHO PHÙ HỢP VỚI NỘI DUNG HIỂN THỊ TRÊN MÀN HÌNH
		this.setVisible(true);
		this.setLocationRelativeTo(null);//GIÚP CỬA SỔ XUẤT HIỆN Ở GIỮA MÀN HÌNH
		
	}
}
