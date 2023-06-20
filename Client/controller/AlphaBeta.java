package controller;

import java.util.ArrayList;

public class AlphaBeta {
	private static final int winScore=100000000;
	private static final int N=16;
	private static final int K=5;
	
	//Mảng arr[][] lưu các giá trị 0,1,-1 trong đó: 0 là ô trống; 1 là ô Máy đã đánh; -1 là ô Người chơi đã đánh
	public static int[] calculateNextMove(int [][]arr, int depth) {
		int[] bestMove = searchWinningMove(arr); 
		int[] badMove = searchLoseMove(arr);
		int[] move = new int[2];
			
		//Nếu tìm được nước Máy (O) thắng ngay sau đó thì ta đánh nước đó luôn
		if(bestMove[0] != -1 && bestMove[1] != -1) return bestMove;
			
		//Nếu tìm được nước Máy (O) thua ngay sau đó thì ta chặn lại nước đó luôn
		if (badMove[0] != -1 && badMove[1] != -1) return badMove;
			
		double[] alphaBetaMove = alphaBeta(depth, arr, 1, -1.0, winScore);
		if(alphaBetaMove[0] == -1) {
			move[0] =-1;
			move[1]	=-1;
		} else {
			move[0] = (int)(alphaBetaMove[0]);
			move[1] = (int)(alphaBetaMove[1]);
		}
		return move;
	}
	
	//Tìm kiếm nước đi thắng ngay
	public static int[] searchWinningMove(int[][] arr) {
		ArrayList<int[]> allPossibleMoves = generateMoves(arr); //Mảng lưu những ô trống bên cạnh nó có đánh X hoặc O liền kề
		int[] winningMove = new int[2];
			
		for(int[] move : allPossibleMoves) {
			int[][] nextArr = addToTable(move[0], move[1], 1, arr);//Sao chép sang mảng mới và thêm nước đi mới cho Máy
				
			if(Heuristic.getScore(nextArr,1,-1) >= winScore) {//Nếu nước đi vừa duyệt là Máy thắng thì trả về vị trí đó
				winningMove[0] = move[0];
				winningMove[1] = move[1]; 
				return winningMove;
			}  
		}
		winningMove[0]=-1;
		winningMove[1]=-1;
		return winningMove;
	}
		
	//Tìm kiếm nước đi thua ngay
	public static int[] searchLoseMove(int[][] arr) {
		ArrayList<int[]> allPossibleMoves = generateMoves(arr); //Mảng lưu những ô trống bên cạnh nó có đánh X hoặc O liền kề
		int[] losingMove = new int[2];
			
		for(int[] move : allPossibleMoves) {
			int[][] nextArr = addToTable(move[0], move[1], -1, arr);//Sao chép sang mảng mới và thêm nước đi mới cho Người chơi
			
			if (Heuristic.getScore(nextArr,-1,1) >= winScore) {//Nếu nước đi vừa duyệt là Người chơi thắng thì trả về vị trí đó
				losingMove[0] = move[0];
				losingMove[1] = move[1];
				return losingMove;
			} 
		}
		losingMove[0]=-1;
		losingMove[1]=-1;
		return losingMove;
	}

	//Hàm tìm nước đi cho máy bằng thuật toán alphabeta 
	//Mảng arr[][] lưu các giá trị 0,1,-1 trong đó: 0 là ô trống; 1 là ô Máy đã đánh; -1 là ô Người chơi đã đánh
	//turn: là đánh dấu xem đến lượt chơi của ai.      turn=1 là đến lượt Máy;       turn=-1 là đến lượt Người chơi
	public static double[] alphaBeta(int depth ,int[][] arr, int turn, double alpha, double beta) {
	
		if(depth == 0) {//Nếu độ sâu là 0 thì trả về điểm nước cờ đó theo hàm scoreBetweenTwoPlayers		
			double[] x = {-1, -1, Heuristic.scoreBetweenTwoPlayers(arr, turn)};
			return x;								
		}
		
		ArrayList<int[]> allPossibleMoves = generateMoves(arr); //Mảng các ô cần duyệt (chỉ duyệt các ô kề các ô đã đánh)
		
		//Nếu đã đánh kín bàn cờ, k còn ô để duyệt
		if(allPossibleMoves.size() == 0) {
			double[] x = {-1, -1, Heuristic.scoreBetweenTwoPlayers(arr, turn)};
			return x;
		}
		
		//result[2] sẽ lưu điểm của bàn cờ; result[0] và result[1] lưu vị trí của nước cờ vừa đánh
		double[] result = new double[3];		
		if(turn==1) {
			result[2]=-1.0;
			for(int[] move : allPossibleMoves) {
				int[][] nextArr = addToTable(move[0],move[1],turn,arr);//Tạo mảng mới giống mảng cũ và thêm nước đi mới cho Máy (Người chơi)
				
				double[] tempMove = alphaBeta(depth-1, nextArr, -turn, alpha, beta);
				if(tempMove[2] > result[2]) {
					result[2] = tempMove[2];
					result[0] = move[0];
					result[1] = move[1];
					alpha = Math.max(alpha, result[2]);
				}
				if(alpha>=beta) {
					return result;
				}
			}
			
		} else {
			result[2]= winScore;
			for(int[] move : allPossibleMoves) {
				int[][] nextArr = addToTable(move[0],move[1],turn,arr);//Tạo mảng mới giống mảng cũ và thêm nước đi mới cho Máy (Người chơi)
				double[] tempMove = alphaBeta(depth-1, nextArr, -turn, alpha, beta);	
				if(tempMove[2] <result[2]) {
					result[2] = tempMove[2];
					result[0] = move[0];
					result[1] = move[1];
					beta = Math.min(beta, result[2]);
				}
				if(alpha>=beta) {
					return result;
				}
			}	
		}	
		return result;
	}
	
	//Sao chép sang mảng mới và thêm nước đi mới cho Máy (hoặc Người chơi)
	private static int[][] addToTable(int i, int j, int value, int[][] arr) {
		int[][] result = new int[N][N];
		for(int index = 0; index < N; index++) { //sao chép mảng 2 chiều
			System.arraycopy(arr[index], 0, result[index], 0, N);
		}		
		result[i][j] = value;
		return result;	
	}
	
	//Hàm lưu tất cả những ô trống mà bên cạnh nó có đánh X hoặc O liền kề
	public static ArrayList<int[]> generateMoves(int[][] arr) {
		ArrayList<int[]> moveList = new ArrayList<int[]>();
			
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				
				if(arr[i][j] != 0) continue;
				
				if(i > 0) {
					if(j > 0) { 
						if(arr[i-1][j-1] != 0 || arr[i][j-1] != 0) {
							int[] move = {i,j};
							moveList.add(move);
							continue;
						}
					}
					if(j < N-1) {
						if(arr[i-1][j+1] != 0 || arr[i][j+1] != 0) {
							int[] move = {i,j};
							moveList.add(move);
							continue;
						}
					}
					if(arr[i-1][j] != 0) {
						int[] move = {i,j};
						moveList.add(move);
						continue;
					}
				}
					
				if( i < N-1) {
					if(j > 0) { 
						if(arr[i+1][j-1] != 0 || arr[i][j-1] != 0) {
							int[] move = {i,j};
							moveList.add(move);
							continue;
						}
					}
					if(j < N-1) { 
						if(arr[i+1][j+1] != 0 || arr[i][j+1] != 0) {
							int[] move = {i,j};
							moveList.add(move);
							continue;
						}
					}
					if(arr[i+1][j] != 0) {
						int[] move = {i,j};
						moveList.add(move);
						continue;
					}
				}
			}
		}
		return moveList;
	}	
}



