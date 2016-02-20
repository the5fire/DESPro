package mydes;

/**
 * 就当做是界面类吧，用来测试结果。
 * @author the5fire
 * blog:http://www.the5fire.net
 *
 */
public class DESView {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DES des = new DES();
		
		int[] data = new int[]{
				0,0,0,0,0,0,0,1,
				0,0,1,0,0,0,1,1,
				0,1,0,0,0,1,0,1,
				0,1,1,0,0,1,1,1,
				1,0,0,0,1,0,0,1,
				1,0,1,0,1,0,1,1,
				1,1,0,0,1,1,0,1,
				1,1,1,0,1,1,1,1
		};
		int[] key = new int[]{
				0,0,0,1,0,0,1,1,
				0,0,1,1,0,1,0,0,
				0,1,0,1,0,1,1,1,
				0,1,1,1,1,0,0,1,
				1,0,0,1,1,0,1,1,
				1,0,1,1,1,1,0,0,
				1,1,0,1,1,1,1,1,
				1,1,1,1,0,0,0,1
		};
		
		System.out.println("明文为：");
		for (int i = 0; i < data.length; i++) {
			System.out.print(data[i]);
			if ((i+1) % 8 == 0) {
				System.out.print("    ");
			}
		}
		System.out.println("");
		
		int[] result = des.encrypt(data, key);
		System.out.println("加密后的结果为：");
		for (int i = 0; i < result.length; i++) {
			System.out.print(result[i]);
			if ((i+1) % 8 == 0) {
				System.out.print("    ");
			}
		}
		System.out.println("");
		
		int[] M = des.decryption(result, key);
		
		System.out.println("解密后的结果为：");
		for (int i = 0; i < M.length; i++) {
			System.out.print(M[i]);
			if ((i+1) % 8 == 0) {
				System.out.print("    ");
			}
		}
		
		
	}

}
