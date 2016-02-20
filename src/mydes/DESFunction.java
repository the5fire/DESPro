package mydes;

/**
 * DES�㷨��Ҫ�Ļ�������������˵��ʼ�û����ȡ�
 * @author the5fire
 * blog:http://www.the5fire.net
 *
 */
public class DESFunction {
	
	/**
	 * ����ģ2������
	 * @param left
	 * @param right
	 * @return
	 */
	public int[] divPlus(int[] left, int[] right) {
		//���жϳ����Ƿ�һ�£���һ�·���
		if(left.length != right.length || left.length == 0 || right.length == 0) {
			throw new RuntimeException("ģ2���г��ȱ���һ�� !");
		}
		
		int[] tmpResult = new int[left.length];
		
		for(int i = 0; i < left.length; i++) {
			tmpResult[i] = (left[i] + right[i]) % 2;
		}
		
		return tmpResult;
	}
	
	/**
	 * ��ʼ���û�IP
	 * @param M��Ϊ64λ������
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
	 * �õ�ʮ��������Կ
	 * @param key
	 * @return
	 */
	public int[][] getSubkey(int[] key) {
		int[][] tmpSubkey = new int[16][48]; 
		int[] tmpKey = new int[56];
		
		//1.���ȶ�key��ѡ���û�
		for (int i = 0; i < 56; i++) {
			tmpKey[i] = key[IConst.PC_1[i] - 1];
		}
		
		for (int i = 0; i < 16; i++) {
			//����˽�з���
            this.leftBitMove(tmpKey, IConst.LeftMove[i]);
            
            //��������Կ
            for (int j = 0; j < 48; j++) {
                tmpSubkey[i][j] = tmpKey[IConst.PC_2[j] - 1]; 
            }
        }
		
		return tmpSubkey;
	}
	
	/**
	 * ˽�÷�������������ѭ������
	 * @param k
	 * @param offset
	 */
	private void leftBitMove(int[] k, int offset) {
        int i;
        // ѭ����λ��������
        int[] c0 = new int[28];
        int[] d0 = new int[28];
        int[] c1 = new int[28];
        int[] d1 = new int[28];
        
        //1�����Ƚ����з�Ϊ������
        for (i = 0; i < 28; i++) {
            c0[i] = k[i];
            d0[i] = k[i + 28];
        }
        
        //2����λ�ƶ�
        if (offset == 1) {
            for (i = 0; i < 27; i++) { // ѭ������һλ
                c1[i] = c0[i + 1];
                d1[i] = d0[i + 1];
            }
            c1[27] = c0[0];
            d1[27] = d0[0];
        } else if (offset == 2) {
            for (i = 0; i < 26; i++) { // ѭ��������λ
                c1[i] = c0[i + 2];
                d1[i] = d0[i + 2];
            }
            c1[26] = c0[0];
            d1[26] = d0[0];
            c1[27] = c0[1];
            d1[27] = d0[1];
        }
        //���ô��ݣ��ٴθ�ֵ��k��
        for (i = 0; i < 28; i++) {
            k[i] = c1[i];
            k[i + 28] = d1[i];
        }
    } 
	
	/**
	 * ���ļ��㺯��
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
		
		//1.�Ƚ�R����E�任
		for (int i = 0; i < extendR.length; i++) {
			extendR[i] = R[IConst.E[i] - 1];
		}
		
		//2.ģ2��,�������extendR��
		extendR = this.divPlus(extendR, K);

		//3.
		for(int i = 0; i < 8; i++) {
			//����
			for (int j = 0; j < 6; j++) {
				S[i][j] = extendR[i * 6 + j];
			}
			
			//S�б任��ʹ������������в�������Ϊ������Ҫ����Ӧλƴ������������֡�
			sBoxData[i] = IConst.S_Box[i][(S[i][0] << 1) + S[i][5]][(S[i][1] << 3) +
			              (S[i][2] << 2) + (S[i][3] << 1) + S[i][4]];
			
			//�����ɵ�8������ת�ɶ����ƴ浽sValue��
			for (int j = 0; j < 4; j++) {
                sValue[((i * 4) + 3) - j] = sBoxData[i] % 2;
                sBoxData[i] = sBoxData[i] / 2;
            }
		}
		
		
		//4.P�任
		for (int i = 0; i < 32; i++) {
			tmpResult[i] = sValue[IConst.P[i] - 1];
		}
		
		return tmpResult;
	}
	
	/**
	 * �������ʼ�û�
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
