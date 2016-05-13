package media.willis.com.gamma_tuning;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengzixiang on 16-5-3.
 */
public class Gamma {
    public final static int GAMMA_X_TABLE_COUNT = 32;
    public final static int GAMMA_X_VALUE_MAX = 16384;
    private static Gamma ourInstance = new Gamma();
    private gamma_map gammaMap = null;
    private int gamma_x_table_default[]  = {
            0,     128,   256,   384,   512,   640,   768,   896,
            1024,  1024+128, 1280, 1536,  1792,  2048,  2304,  2560,
            2816,3072,  3072+256,  3584, 4096,  4608,  5120,  5120+512,
            6144, 6144+512, 7168, 8192, 10240, 12288, 14336, 16384
    };
    /*
    private float[] gamma_s_shape_table_default = {
            0.1f, 0.52f, 0.55f, 0.58f, 0.61f, 0.64f, 0.67f, 0.7f,
            0.73f, 0.76f, 0.79f, 0.85f, 0.91f, 0.96f, 1.01f, 1.015f,
            1.022f, 1.038f, 1.055f, 1.065f, 1.078f, 1.09f, 1.10f, 1.105f,
            1.10f, 1.087f, 1.075f, 1.06f, 1.045f, 1.030f, 1.015f, 1.f,
    };
    */
    private float[]  gamma_s_shape_table_default = {
            0.1f, 0.1f, 0.15f, 0.2f, 0.25f, 0.3f, 0.35f, 0.4f,
            0.45f, 0.5f, 0.55f, 0.7f, 0.85f, 0.95f, 1.015f, 1.04f,
            1.06f, 1.07f, 1.08f, 1.085f, 1.09f, 1.09f, 1.09f, 1.09f,
            1.09f, 1.085f, 1.078f, 1.065f, 1.04f, 1.025f, 1.01f, 1.f,
    };
    public static Gamma getInstance() {
        return ourInstance;
    }

    private Gamma() {
        init();
    }


    void init() {
        gammaMap = new gamma_map();

        gammaMap.normalGamma = 2.2f;
        gammaMap.normalBaseOffset = -128;
        gammaMap.normalEndOffset = 0;

        gammaMap.indoorGamma = 2.0f;
        gammaMap.indoorBaseOffset = 0;
        gammaMap.indoorEndOffset = 0;

        gammaMap.outdoorGamma = 2.4f;
        gammaMap.outdoorBaseOffset = -128;
        gammaMap.outdoorEndOffset = -2048;

        for (int i = 0; i < GAMMA_X_TABLE_COUNT; i++) {
            gammaMap.gamma_x_table[i] = gamma_x_table_default[i];
            gammaMap.gamma_s_shape_table[i] = gamma_s_shape_table_default[i];
        }

    }

    void setGammaExponent(int gammaType, float gammaExponent) {
        switch( gammaType) {
            case gamma_type.GAMMA_NORMAL:
                gammaMap.normalGamma = gammaExponent;
                break;
            case gamma_type.GAMMA_INDOOR:
                gammaMap.indoorGamma = gammaExponent;
                break;
            case gamma_type.GAMMA_OUTDOOR:
                gammaMap.outdoorGamma = gammaExponent;
                break;
            default:
                return;
        }
    }
    float getGammaExponent(int gammaType) {
        switch( gammaType) {
            case gamma_type.GAMMA_NORMAL:
                return gammaMap.normalGamma;
            case gamma_type.GAMMA_INDOOR:
                return gammaMap.indoorGamma;
            case gamma_type.GAMMA_OUTDOOR:
                return gammaMap.outdoorGamma;
            default:
                return 1.f;
        }
    }
    void setGammaLowOffset(int gammaType, int gammaLowOffset) {
        switch( gammaType) {
            case gamma_type.GAMMA_NORMAL:
                gammaMap.normalBaseOffset = gammaLowOffset;
                break;
            case gamma_type.GAMMA_INDOOR:
                gammaMap.indoorBaseOffset = gammaLowOffset;
                break;
            case gamma_type.GAMMA_OUTDOOR:
                gammaMap.outdoorBaseOffset = gammaLowOffset;
                break;
            default:
                return;
        }
    }
    int getGammaLowOffset(int gammaType) {
        switch( gammaType) {
            case gamma_type.GAMMA_NORMAL:
                return gammaMap.normalBaseOffset;
            case gamma_type.GAMMA_INDOOR:
                return gammaMap.indoorBaseOffset;
            case gamma_type.GAMMA_OUTDOOR:
                return gammaMap.outdoorBaseOffset;
            default:
                return 0;
        }
    }
    void setGammaHighOffset(int gammaType, int gammaHighOffset) {
        switch( gammaType) {
            case gamma_type.GAMMA_NORMAL:
                gammaMap.normalEndOffset = gammaHighOffset;
                break;
            case gamma_type.GAMMA_INDOOR:
                gammaMap.indoorEndOffset = gammaHighOffset;
                break;
            case gamma_type.GAMMA_OUTDOOR:
                gammaMap.outdoorEndOffset = gammaHighOffset;
                break;
            default:
                return;
        }
    }
    int getGammaHighOffset(int gammaType) {
        switch( gammaType) {
            case gamma_type.GAMMA_NORMAL:
                return gammaMap.normalEndOffset;
            case gamma_type.GAMMA_INDOOR:
                return gammaMap.indoorEndOffset;
            case gamma_type.GAMMA_OUTDOOR:
                return gammaMap.outdoorEndOffset;
            default:
                return 0;
        }
    }

    void setSGamma(float[] sGamma) {

    }

    float[] getSGamma() {
        return gammaMap.gamma_s_shape_table;
    }

    class gamma_map {
        public float normalGamma;
        public int normalBaseOffset;
        public int normalEndOffset;

        public float indoorGamma;
        public int indoorBaseOffset;
        public int indoorEndOffset;

        public float outdoorGamma;
        public int outdoorBaseOffset;
        public int outdoorEndOffset;

        public int[] gamma_x_table = new int[GAMMA_X_TABLE_COUNT];
        public float[] gamma_s_shape_table = new float[GAMMA_X_TABLE_COUNT];


        gamma_map() {
        }
    }

    int [] getGammaTableX() {
        return gammaMap.gamma_x_table;
    }

    List<String> getGammaTypeString() {
        List<String> data = new ArrayList<String>();
        data.add(gamma_type.GAMMA_X_TABLE_S);
        data.add(gamma_type.GAMMA_NORMAL_S);
        data.add(gamma_type.GAMMA_INDOOR_S);
        data.add(gamma_type.GAMMA_OUTDOOR_S);
        data.add(gamma_type.GAMMA_S_SHAPE_S);
        data.add(gamma_type.GAMMA_COMBINATION_S);
        return data;
    }


    String getGammaTypeString(int i) {
        switch (i) {
            case gamma_type.GAMMA_X_TABLE:
                return gamma_type.GAMMA_X_TABLE_S;
            case gamma_type.GAMMA_NORMAL:
                return gamma_type.GAMMA_NORMAL_S;
            case gamma_type.GAMMA_INDOOR:
                return gamma_type.GAMMA_INDOOR_S;
            case gamma_type.GAMMA_OUTDOOR:
                return gamma_type.GAMMA_OUTDOOR_S;
            case gamma_type.GAMMA_S_SHAPE:
                return gamma_type.GAMMA_S_SHAPE_S;
            case gamma_type.GAMMA_COMBINATION:
                return gamma_type.GAMMA_COMBINATION_S;
            default:
                return null;
        }
    }
    private void loadGammaTuningFile() {

    }

    private void saveGammaTuningFile() {

    }

}
