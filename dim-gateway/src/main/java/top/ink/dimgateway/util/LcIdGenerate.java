package top.ink.dimgateway.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class LcIdGenerate {
    private final static String DATA_SOURCE = "0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V," +
            "W,S,Y,Z,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,s,y,z";
    private final static List<String> SOURCE_LIST = Arrays.asList(DATA_SOURCE.split(","));
    private final static Integer LEN = 14;
    private static final Random RANDOM = new Random();

    /**
     * @description 生成userId
     * @author 林北
     * @date 2021-12-04 11:09
     **/
    public static String generateUserId() {
        Collections.shuffle(SOURCE_LIST);
        StringBuilder userId = new StringBuilder("lid_");
        for (int i = 0; i < LEN; i++) {
            userId.append(SOURCE_LIST.get(RANDOM.nextInt(62)));
        }
        return userId.toString();
    }

    /**
     * @description 生成groupId
     * @author 林北
     * @date 2021-12-04 11:09
     **/
    public static String generateGroupId() {
        Collections.shuffle(SOURCE_LIST);
        StringBuilder groupId = new StringBuilder("lid_group_");
        for (int i = 0; i < LEN; i++) {
            groupId.append(SOURCE_LIST.get(RANDOM.nextInt(62)));
        }
        return groupId.toString();
    }
}