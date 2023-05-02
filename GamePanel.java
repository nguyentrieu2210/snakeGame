import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.Random;
import java.awt.event.*;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{

//======* KHAI BÁO*======//
	static final int chieu_ngang = 1000; // CHIỀU NGANG KHUNG TRÒ CHƠI
	static final int chieu_cao = 600;    // CHIỀU CAO KHUNG TRÒ CHƠI
	static final int kich_thuoc_1o = 25;    //KÍCH THƯỚC MỖI Ô
	static final int max = 100;
	static final int DELAY = 75;         // DÙNG ĐỂ ĐIỀU CHỈNH THỜI GIAN GIỮA CÁC SỰ KIỆN
	final int x[] = new int[max];        //TỌA ĐỘ CỦA RẮN
	final int y[] = new int[max];        // TỌA ĐỘ CỦA RẮN
	int doDaiRan =6;                     // ĐỘ DÀI BAN ĐẦU CỦA RẮN
	int diem;                            // ĐIỂM
	int thucAnX;                         // VỊ TRÍ CỦA THỨC ĂN 
	int thucAnY;                         // VỊ TRÍ CỦA THỨC ĂN 
	char huongDiChuyen = 'R';            // HƯỚNG DI CHUYỂN (CON RẮN BẮT ĐẦU ĐI TỪ BÊN PHẢI)
	boolean running = false;             // TRẠNG THÁI
	Timer timer;                         // LỚP TẠO SỰ KIỆN ĐỊNH KỲ TRONG CHƯƠNG TRÌNH
	Random random;
	
//======* END KHAI BÁO *======//
	
//======* CÁC PHƯƠNG THỨC *======//
	//HIỂN THỊ JPANEL
	GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(chieu_ngang,chieu_cao)); //THIẾT LẬP KÍCH THƯỚC ƯU TIÊN CHO 1 THÀNH PHẦN 
		this.setBackground(Color.black);
		this.setFocusable(true); // ĐỐI TƯỢNG NHẬN ĐƯỢC SỰ TẬP TRUNG CỦA NGƯỜI DÙNG KHI ĐƯỢC CHỌN
		this.addKeyListener(new MyKeyAdapter()); // BẮT CÁC SỰ KIỆN TỪ BÀN PHÍM
		startGame();
	}
	//HÀM CHẠY GAME
	public void startGame() {
		taoThucAn();
		running = true;
		timer = new Timer(DELAY,this); //TẠO ĐỐI TƯỢNG TIMER VÀ THỜI GIAN ĐẾM NGƯỢC GIỮA CÁC SỰ KIỆN LÀ DELAY
		timer.start(); //BẮT ĐẦU ĐẾM NGƯỢC 
	}
	//PHUONG THỨC NÀY ĐƯỢC GỌI ĐỂ VẼ LẠI ĐỐI TƯỢNG
	public void paintComponent(Graphics g) {
		super.paintComponent(g);//GỌI LẠI PHƯƠNG THỨC PAINTCOMPONENT CỦA LỚP CHA 
		//(GIÚP ND CỦA ĐỐI TƯỢNG ĐƯỢC VẼ LẠI ĐÚNG CÁCH VÀ HIỂN THỊ ĐÔNG THỜI VỚI CÁC THÀNH PHẦN KHÁC)
		draw(g);//VẼ LẠI NỘI DUNG CỦA ĐỐI TƯỢNG
	}
	
	//VẼ CÁC ĐỐI TƯỢNG
	public void draw(Graphics g) {//Graphics LÀ 1 LỚP TRỪU TƯỢNG
		if(running) {
				//THỨC ĂN
				g.setColor(Color.red); // MÀU THỨC ĂN
				g.fillOval(thucAnX, thucAnY, kich_thuoc_1o, kich_thuoc_1o);// XÁC ĐỊNH HÌNH DÁNG (HÌNH TRÒN) VỊ TRÍ VÀ KÍCH THƯỚC CỦA THỨC ĂN
				
				//RẮN (X[0],Y[0] LÀ TỌA ĐỘ ĐẦU RẮN)
				for(int i=0; i<doDaiRan; i++) {
					if(i==0) {//ĐẦU RẮN
						g.setColor(new Color(45,180,0));
						g.fillRect(x[i], y[i],kich_thuoc_1o ,kich_thuoc_1o);
					}else {//THÂN RẮN
						g.setColor(Color.green);
						g.fillRect(x[i], y[i],kich_thuoc_1o ,kich_thuoc_1o);
					}
				}
				//HIỂN THỊ ĐIỂM 
				g.setColor(Color.white);
				g.setFont(new Font("Ink Free", Font.BOLD, 40));
				FontMetrics fm = getFontMetrics(g.getFont()); //LẤY KÍCH THƯỚC VÀ THÔNG TIN LIÊN QUAN TỚI FONT HIỆN TẠI ĐƯỢ SỬ DỤNG TRONG GRAPHICS 
				g.drawString("Score:"+diem, (chieu_ngang - fm.stringWidth("Score:"+diem))/2, g.getFont().getSize());// VẼ 1 CHUỖI KÍ TỰ (TEXT, TỌA ĐỘ X, TỌA ĐỘ Y)
				//g.getFont().getSize() LẤY KÍCH THƯỚC HIỆN TẠI CỦA FONT CHỮ
				
		}else {
			gameOver(g);
		}
	}
	//HÀM TẠO THỨC ĂN
	public void taoThucAn() {
		thucAnX = random.nextInt((int)(chieu_ngang/kich_thuoc_1o))*kich_thuoc_1o;//TẠO RA 1 SỐ NGUYÊN NGẪU NHIÊN
		thucAnY = random.nextInt((int)(chieu_cao/kich_thuoc_1o))*kich_thuoc_1o;  //TẠO RA 1 SỐ NGUYÊN NGẪU NHIÊN
	}
	//HÀM RẮN DI CHUYỂN
	public void diChuyen(){
		for(int i = doDaiRan;i>0;i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(huongDiChuyen) {
		//ĐI LÊN 
		case 'U':
			y[0] = y[0] - kich_thuoc_1o;
			break;
		//ĐI XUỐNG
		case 'D':
			y[0] = y[0] + kich_thuoc_1o;
			break;
		//ĐI SANG TRÁI
		case 'L':
			x[0] = x[0] - kich_thuoc_1o;
			break;
		//ĐI SANG PHẢI
		case 'R':
			x[0] = x[0] + kich_thuoc_1o;
			break;
		}
		
	}
	//HÀM RẮN ĂN THỨC ĂN
	public void anThucAn() {
		if(x[0]==thucAnX && y[0]==thucAnY) {
			doDaiRan++;
			diem++;
			taoThucAn();
		}
	}
	//KIỂM TRA RẮN VA CHẠM
	public void vaCham() {
		//RẮN CẮN TRÚNG THÂN 
		for(int i = doDaiRan; i>0; i--) {
			if((x[0]==x[i])&& (y[0]==y[i])) {
				running = false;
			}
		}
		//RẮN CHẠY XUYÊN KHÔNG
		// TRÁI
		if(x[0]<0) {
			x[0] = chieu_ngang;
		}
		//PHẢI
		if(x[0]>chieu_ngang) {
			x[0] = 0;
		}
		//TRÊN 
		if(y[0]<0) {
			y[0] = chieu_cao;
		}
		//DƯỚI
		if(y[0]>chieu_cao) {
			y[0] = 0;
		}
	}
	//GAME OVER
	public void gameOver(Graphics g) {
		//HIỂN THỊ GAME OVER
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 110));
		FontMetrics fm2 = getFontMetrics(g.getFont()); // LẤY KÍCH THƯỚC VÀ THÔNG TIN CỦA FONT CHỮ HIỆN TẠI ĐƯỢC SỬ DỤNG TRONG GRAPHICS
		g.drawString("Game Over", (chieu_ngang - fm2.stringWidth("Game Over"))/2, chieu_cao/2); // VẼ 1 CHUỖI KÍ TỰ (TEXT, TỌA ĐỘ X, TỌA ĐỘ Y)
		//NHẤN ENTER ĐỂ CHƠI GAME MỚI
		g.setColor(new Color(45,180,0));
		g.setFont(new Font("Ink Free", Font.BOLD, 30));
		FontMetrics fm3 = getFontMetrics(g.getFont()); // LẤY KÍCH THƯỚC VÀ THÔNG TIN CỦA FONT CHỮ HIỆN TẠI ĐƯỢC SỬ DỤNG TRONG GRAPHICS
		g.drawString("Press ENTER to play New Game", (chieu_ngang - fm3.stringWidth("Nhấn ENTER dể chơi Game Mới"))/2, chieu_cao/2+120); // VẼ 1 CHUỖI KÍ TỰ (TEXT, TỌA ĐỘ X, TỌA ĐỘ Y)
		
		//ĐIỂM 
		g.setColor(Color.green);
		g.setFont(new Font("Ink Free", Font.BOLD, 60));
		FontMetrics fm1 = getFontMetrics(g.getFont());
		g.drawString("Score:"+diem, (chieu_ngang - fm1.stringWidth("Score:"+diem))/2, 150); //VẼ 1 CHUỖI VĂN BẢN TẠI VỊ TRÍ (X, Y)
	}
//======* END PHƯƠNG THỨC *======//
	
//======* BẮT SỰ KIỆN *======//	
	@Override
	public void actionPerformed(ActionEvent e) { //LÀ 1 PHƯƠNG THỨC CỦA LỚP ACTIONLISTENER. ĐƯỢC GỌI TỰ ĐỘNG KHI SỰ KIỆN ĐƯỢC KÍCH HOẠT
		// TODO Auto-generated method stub
		if(running) {
			diChuyen();
			anThucAn();
			vaCham();
		}
		repaint(); //VẼ LẠI, CẬP NHẬT LẠI CÁC THÀNH PHẦN
	}
	//BẮT VÀ XỬ LÝ SỰ KIỆN TỪ BÀN PHÍM
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) { //BẮT SỰ KIÊN VÀ XỬ LÝ KHI 1 PHÍM ĐƯỢC BẤM XUỐNG
			switch (e.getKeyCode()) { //PHƯƠNG THỨC GETkEYCODE() ĐƯỢC DÙNG ĐỂ LẤY MÃ CỦA PHÍM VỬA ĐƯỢC BẤM XUỐNG
			case KeyEvent.VK_LEFT:
				if(huongDiChuyen!='R') {
					huongDiChuyen = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(huongDiChuyen!='L') {
					huongDiChuyen = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(huongDiChuyen!='D') {
					huongDiChuyen = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(huongDiChuyen!='U') {
					huongDiChuyen = 'D';
				}
				break;
			case KeyEvent.VK_ENTER:
				new GameFrame();
				break;
			}
		}
	}

}
