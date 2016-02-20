package mydes;

/**
 * DES算法需要的基本操作，比如说初始置换，等。
 * @author the5fire
 * blog:http://www.the5fire.net
 *
 */
public class DESFunction {
	
	/**
	 * 定义模2加运算
	 * @param left
	 * @param right
	 * @return
	 */
	public int[] divPlus(int[] left, int[] right) {
		//先判断长度是否一致，不一致返回
		if(left.length != right.length || left.length == 0 || right.length == 0) {
			throw new RuntimeException("模2加中长度必须一致 !");
		}
		
		int[] tmpResult = new int[left.length];
		
		for(int i = 0; i < left.length; i++) {
			tmpResult[i] = (left[i] + right[i]) % 2;
		}
		
		return tmpResult;
	}
	
	/**
	 * 初始化置换IP
	 * @param M，为64位的明文
	 * @return
	 */
	public int[] initReplace(int[] M) {
		int[] tmpM = new int[M.length];
		
		for (int i = 0; i < tmpM.length; i++) {
			tmpM[i] = M[IConst.IP[i] - 1];
		}
		
		return tmpM;
	}
	
	/**
	 * 得到十六个子密钥
	 * @param key
	 * @return
	 */
	public int[][] getSubkey(int[] key) {
		int[][] tmpSubkey = new int[16][48]; 
		int[] tmpKey = new int[56];
		
		//1.首先对key做选择置换
		for (int i = 0; i < 56; i++) {
			tmpKey[i] = key[IConst.PC_1[i] - 1];
		}
		
		for (int i = 0; i < 16; i++) {
			//调用私有方法
            this.leftBitMove(tmpKey, IConst.LeftMove[i]);
            
            //生成子密钥
            for (int j = 0; j < 48; j++) {
                tmpSubkey[i][j] = tmpKey[IConst.PC_2[j] - 1]; 
            }
        }
		
		return tmpSubkey;
	}
	
	/**
	 * 私用方法，用来进行循环左移
	 * @param k
	 * @param offset
	 */
	private void leftBitMove(int[] k, int offset) {
        int i;
        // 循环移位操作函数
        int[] c0 = new int[28];
        int[] d0 = new int[28];
        int[] c1 = new int[28];
        int[] d1 = new int[28];
        
        //1。首先将序列分为两部分
        for (i = 0; i < 28; i++) {
            c0[i] = k[i];
            d0[i] = k[i + 28];
        }
        
        //2。按位移动
        if (offset == 1) {
            for (i = 0; i < 27; i++) { // 循环左移一位
                c1[i] = c0[i + 1];
                d1[i] = d0[i + 1];
            }
            c1[27] = c0[0];
            d1[27] = d0[0];
        } else if (offset == 2) {
            for (i = 0; i < 26; i++) { // 循环左移两位
                c1[i] = c0[i + 2];
                d1[i] = d0[i + 2];
            }
            c1[26] = c0[0];
            d1[26] = d0[0];
            c1[27] = c0[1];
            d1[27] = d0[1];
        }
        //引用传递，再次赋值到k中
        for (i = 0; i < 28; i++) {
            k[i] = c1[i];
            k[i + 28] = d1[i];
        }
    } 
	
	/**
	 * 核心计算函数
	 * @param R
	 * @param K
	 * @return
	 */
	public int[] coreFunction(int[] R, int[] K) {
		int[] tmpResult = new int[32];
		int[] extendR = new int[48];
		int[][] S = new int[8][6];
		int[] sBoxData = new int[8];
		int[] sValue = new int[32];
		
		//1.先将R进行E变换
		for (int i = 0; i < extendR.length; i++) {
			extendR[i] = R[IConst.E[i] - 1];
		}
		
		//2.模2加,结果存在extendR中
		extendR = this.divPlus(extendR, K);

		//3.
		for(int i = 0; i < 8; i++) {
			//分组
			for (int j = 0; j < 6; j++) {
				S[i][j] = extendR[i * 6 + j];
			}
			
			//S盒变换，使用左移运算进行操作，因为这里是要把相应位拼起来，组成数字。
			sBoxData[i] = IConst.S_Box[i][(S[i][0] << 1) + S[i][5]][(S[i][1] << 3) +
			              (S[i][2] << 2) + (S[i][3] << 1) + S[i][4]];
			
			//把生成的8个数字转成二进制存到sValue中
			for (int j = 0; j < 4; j++) {
                sValue[((i * 4) + 3) - j] = sBoxData[i] % 2;
                sBoxData[i] = sBoxData[i] / 2;
            }
		}
		
		
		//4.P变换
		for (int i = 0; i < 32; i++) {
			tmpResult[i] = sValue[IConst.P[i] - 1];
		}
		
		return tmpResult;
	}
	
	/**
	 * 最后的逆初始置换
	 * @param C
	 * @return
	 */
	public int[] endReplace(int[] C) {
		int[] tmpC = new int[C.length];
		
		for (int i = 0; i < tmpC.length; i++) {
			tmpC[i] = C[IConst.IP_1[i] - 1];
		}
		
		return tmpC;
	}
	
}
