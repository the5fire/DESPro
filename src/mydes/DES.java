package mydes;

/**
 * DES类，用来加密二进制数据
 * @author the5fire
 * blog:http://www.the5fire.net
 * 
 */
public class DES {
	
	private DESFunction desFunction = new DESFunction();
	/**
	 * 传进二进制明文，返回DES加密后的密文
	 * @param M
	 * @return
	 */
	public int[] encrypt(int[] M, int[] key) {
		int[] C = null;
		int[] M0 = new int[64];
		int[][] L0 = new int[17][32];
		int[][] R0 = new int[17][32];
		int[][] subkey = null;
		int[] result = new int[64];
		
		subkey = desFunction.getSubkey(key);
		
		M0 = desFunction.initReplace(M);

		for (int j = 0; j < 32; j++) {
            L0[0][j] = M0[j]; // 明文左侧的初始化
            R0[0][j] = M0[j + 32]; // 明文右侧的初始化
        }

		for (int i = 1; i < 17; i++) {
			int[] tmpR = desFunction.divPlus(L0[i-1], desFunction.coreFunction(R0[i-1], subkey[i-1]));
			for (int j = 0; j < 32; j++) {
				L0[i][j] = R0[i-1][j];
				R0[i][j] = tmpR[j];
			}
			
		}
		
		//进行赋值，R16放到左侧，也就是前面32位；L16放到右侧，也就是最后32位
		for (int i = 0; i < 32; i++) {
			result[i] = R0[16][i];
		}
		
		for (int i = 0; i < 32; i++) {
			result[32+i] = L0[16][i];
		}
		
		C = desFunction.endReplace(result);
		
		return C;
	}
	
	/**
	 * 传进DES加密后的密文，返回二进制明文
	 * @param C
	 * @return
	 */
	public int[] decryption(int[] C, int[] K) {
		int[] M = null;
		int[] C0 = new int[64];
		int[][] L0 = new int[17][32];
		int[][] R0 = new int[17][32];
		int[][] subkey = null;
		int[] result = new int[64];
		
		subkey = desFunction.getSubkey(K);
		
		C0 = desFunction.initReplace(C);

		for (int j = 0; j < 32; j++) {
            L0[0][j] = C0[j]; // 明文左侧的初始化
            R0[0][j] = C0[j + 32]; // 明文右侧的初始化
        }

		for (int i = 1; i < 17; i++) {
			int[] tmpR = desFunction.divPlus(L0[i-1], desFunction.coreFunction(R0[i-1], subkey[16-i]));
			for (int j = 0; j < 32; j++) {
				L0[i][j] = R0[i-1][j];
				R0[i][j] = tmpR[j];
			}
		}
		
		//进行赋值，R16放到左侧，也就是前面32位；L16放到右侧，也就是最后32位
		for (int i = 0; i < 32; i++) {
			result[i] = R0[16][i];
		}
		
		for (int i = 0; i < 32; i++) {
			result[32+i] = L0[16][i];
		}
		
		M = desFunction.endReplace(result);
		return M;
	}
}
