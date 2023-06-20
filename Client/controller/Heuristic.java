package controller;

public class Heuristic {
	private static int N=16;

	//Hàm tính tỉ số điểm giữa Máy trên Người chơi
	//nextPlayer: lượt đánh tiếp theo là ai?	   1: lượt tiếp theo là Máy     -1: lượt tiếp theo Người chơi
	public static double scoreBetweenTwoPlayers(int[][] arr, int nextPlayer) {  
		double machine = getScore(arr, 1, nextPlayer);	//Tính điểm cho Máy
		double player = getScore(arr, -1, nextPlayer);	//Tính điểm cho Người chơi
		if(player == 0) player = 1.0;
		return machine / player;
	}
	
	
	//Hàm tính điểm TỔNG số điểm hàng ngang, hàng dọc, và 2 đường chéo của 
	//forX: tính điểm cho Máy hay Người chơi?      1: tính điểm cho Máy     	-1: tính điểm cho Người chơi   
	//nextTurn: lượt đánh tiếp theo là ai?	   1: lượt tiếp theo là Máy     -1: lượt tiếp theo Người chơi
	public static int getScore(int[][] arr, int forX, int nextTurn) {
		return evaluateHorizontal(arr, forX, nextTurn) + 
			evaluateVertical(arr, forX, nextTurn) +
			evaluateDiagonal(arr, forX, nextTurn);
	}
		
	//Điểm chiều ngang
	public static int evaluateHorizontal(int[][] arr, int forX, int nextTurn) {		
		
		int consecutive = 0;  	// consecutive: là biến đếm số quân liên tiếp của (forX)
		int blocks = 2;			// blocks: là biến đếm xem dãy quân liên tiếp có đang bị chặn không
		int score = 0;			// score: điểm
			
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
					
				// Gặp quân đang xét 
				if(arr[i][j] == forX)  consecutive++;    //Đếm số quân liên tiếp có giá trị forX 
					
				// Gặp ô trống
				else if(arr[i][j] == 0) {
					if(consecutive > 0) { //Nếu trước đó có dãy ô liên tiếp trước đó thì ta tính điểm dãy ô đó
						blocks--;
						score += getConsecutiveSetScore(consecutive, blocks, forX == nextTurn);
						consecutive = 0;
						blocks = 1;
					}
					else blocks = 1;
				}
					
				//Gặp quân địch
				else if(consecutive > 0) { //Nếu trước đó có dãy ô liên tiếp trước đó thì ta tính điểm dãy ô đó
					score += getConsecutiveSetScore(consecutive, blocks, forX == nextTurn);
					consecutive = 0;
					blocks = 2;
				}
				else blocks = 2;
			}
				
			//Duyệt hết 1 dòng
			if(consecutive > 0) {  //Nếu trước đó có dãy ô liên tiếp trước đó thì ta tính điểm dãy ô đó
				score += getConsecutiveSetScore(consecutive, blocks, forX == nextTurn);	
			}
			consecutive = 0;
			blocks = 2;			
		}
			
		return score;
	}
		
		
	//Hàm tính điểm chiều dọc tương tự như chiều ngang
	public static  int evaluateVertical(int[][] arr, int forX, int nextTurn) {
		int consecutive = 0;
		int blocks = 2;
		int score = 0;
			
		for(int j=0; j<N; j++) {
			for(int i=0; i<N; i++) {
				// Gặp quân đang xét
				if(arr[i][j] == forX) consecutive++;
				// Gặp ô trống
				else if(arr[i][j] == 0) {
					if(consecutive > 0) {
						blocks--;
						score += getConsecutiveSetScore(consecutive, blocks, forX == nextTurn);
						consecutive = 0;
						blocks = 1;
					}
					else blocks = 1;
				}
				// Gặp quân địch
				else if(consecutive > 0) {
					score += getConsecutiveSetScore(consecutive, blocks, forX == nextTurn);
					consecutive = 0;
					blocks = 2;
				}
				else blocks = 2;
			}
			//Duyệt hết 1 cột
			if(consecutive > 0) score += getConsecutiveSetScore(consecutive, blocks, forX == nextTurn);
			consecutive = 0;
			blocks = 2;
		}
			
		return score;
	}
		
	//Hàm tính toán 2 đường chéo tương tự như hàng ngang
	public static  int evaluateDiagonal(int[][] arr, int forX, int nextTurn) {
		int consecutive = 0;
		int blocks = 2;
		int score = 0;
		
		// Đường chéo phải
		for (int k = 0; k <= 2 * (N - 1); k++) {
		    int iStart = Math.max(0, k - N + 1);
		    int iEnd = Math.min(N - 1, k);
		    for (int i = iStart; i <= iEnd; ++i) {
		        int j = k - i;
			        
		        // Gặp quân đang xét
		        if(arr[i][j] == forX) {
		        	consecutive++;
				}
		        // Gặp ô trống
				else if(arr[i][j] == 0) {
					if(consecutive > 0) {
						blocks--;
						score += getConsecutiveSetScore(consecutive, blocks, forX==nextTurn);
						consecutive = 0;
						blocks = 1;
					}
					else blocks = 1;
				}
		        // Gặp quân địch					
		        else if(consecutive > 0) {
					score += getConsecutiveSetScore(consecutive, blocks, forX==nextTurn);
					consecutive = 0;
					blocks = 2;
				}
				else blocks = 2;  
		    }
		    //Duyệt hết 1 đường chéo
		    if(consecutive > 0) score += getConsecutiveSetScore(consecutive, blocks, forX==nextTurn);
			consecutive = 0;
			blocks = 2;
		}
			
			
		// Đường chéo trái
		for (int k = 1-N; k < N; k++) {
		    int iStart = Math.max(0, k);
		    int iEnd = Math.min(N + k - 1, N-1);
		    for (int i = iStart; i <= iEnd; ++i) {
		        int j = i - k;
		        // Gặp quân đang xét
		        if(arr[i][j] == forX) consecutive++;
		        // Gặp ô trống
				else if(arr[i][j] == 0) {
					if(consecutive > 0) {
						blocks--;
						score += getConsecutiveSetScore(consecutive, blocks, forX==nextTurn);
						consecutive = 0;
						blocks = 1;
					}
					else blocks = 1;
				}
		        // Gặp quân địch
				else if(consecutive > 0) {
					score += getConsecutiveSetScore(consecutive, blocks, forX==nextTurn);
					consecutive = 0;
					blocks = 2;
				}
				else blocks = 2;
		    }			  
		    //Duyệt hết 1 đường chéo
		    if(consecutive > 0) score += getConsecutiveSetScore(consecutive, blocks, forX==nextTurn);
			consecutive = 0;
			blocks = 2;
		}
		return score;
	}
		
		
	//Hàm tính điểm 
	//consecutive: là biến đếm số quân liên tiếp của
	//blocks: là biến đếm xem dãy quân liên tiếp có đang bị chặn không (blocks=0;1;2)
	//nextTurn: lượt đánh tiếp theo là của ai?  (true: lượt tiếp theo là lượt của quân đang tính điểm)
												//(false: lượt tiếp theo không phải là lượt của quân đang tính điểm)
	public static int getConsecutiveSetScore(int consecutive, int blocks, boolean nextTurn) {
		final int winScore = 100000000;
		final int winGuarantee = 1000000;

		if (blocks == 2 && consecutive <= 5)
			return 0; // Nếu bị chặn 2 đầu thì 0 điểm

		switch (consecutive) {
			case 5: return winScore; // 5 ô liên tiếp Cho điểm cao nhất

			case 4: {
				// tính điểm: 1.000.000 - nếu lượt tiếp theo là lượt của quân đang tính điểm
				if (nextTurn) return winGuarantee;

				// Nếu lượt tiếp theo không phải là lượt của quân đang tính điểm
				else {
					if (blocks == 0) return winGuarantee / 4; // Nếu không bị chặn thì cho 250 000 điểm
					else return 200; // Nếu bị chặn 1 đầu thì cho 250 000 điểm
				}
			}
			case 3: {
				// Nếu lượt tiếp theo là lượt của quân đang tính điểm
				if (nextTurn) {
					if (blocks == 0) return 50000; // Nếu k bị chặn thì đi thêm 1 nước nữa khả năng thắng cao. (3+1=4)
					else return 10;
				}
				// Nếu lượt tiếp theo không phải là lượt của quân đang tính điểm
				else {
					if (blocks == 0) return 200;
					else return 5;
				}
			}
			case 2: {
				// Nếu lượt tiếp theo là lượt của quân đang tính điểm
				if (nextTurn) {
					if (blocks == 0) return 7;
					else return 3;
				}
				// Nếu lượt tiếp theo không phải là lượt của quân đang tính điểm
				else {
					if (blocks == 0) return 5;
					else return 3;
				}
			}
			case 1: return 1;
		}
		return winScore * 2;
	}
}
