package example;

import java.util.Random;

public class MapGenerator {
	public int squareSize = 20;
	private int sx = Window.WIDTH / squareSize;
	private int sy = Window.HEIGHT / squareSize;
	private int randomFillPercent = 45;
	
	public String seed = "2Ras-3aWX-XYOQ-19XP";
	public boolean useRandomSeed = false;
	
	public int[][] map; 
	
	
	public void MapGeneration() {
		map = new int[sx][sy];
		RandomFillMap();
		for(int i = 0; i < 5; i++){ // Sorts the map multiple times, to remove as much noise as possible.
			FixMap();
		}
	}
	
	private void RandomFillMap(){
		// Will take string inputs to generate a map out of it.
		
		if(useRandomSeed){
			seed = String.valueOf(System.currentTimeMillis());
		}
		Random ran = new Random(seed.hashCode());
		
		for(int x = 0; x < sx; x++ ){
			for(int y = 0; y < sy; y++){
				int prng = ran.nextInt(100 - 1) + 1;
				// First creates a border of wall
				if(x == 0 || x == sx-1 || y == 0 || y == sy-1){
					map[x][y] = 0;
				}
				else if(prng < randomFillPercent){
					map[x][y] = 0;
				}
				else if(prng > randomFillPercent){
					map[x][y] = 1;
				}
			}
		}
	}
	
	private void FixMap(){
		// Sorts the map, and creates a fully usable map to play in.
		for(int x = 1; x < sx-1; x++ ){
			for(int y = 1; y < sy-1; y++){
				int sum = 0;
				for(int kx = -1; kx <= 1; kx++ ){
					for(int ky = -1; ky <= 1; ky++){
						 sum += map[x + kx][y + ky];
					}
				}
				if(sum > 4) {
					map[x][y] = 1;
				}
				else {
					map[x][y] = 0;
				}
			}
		}
	}

}
