package sample;


import java.awt.*;

public class ColorMap {
    private Color[] colors;


    public ColorMap() {
        colors = new Color[64];
        colors[0] = new Color((float) 0, (float) 0, (float) 0.5625);
        colors[1] = new Color((float) 0, (float) 0, (float) 0.6250);
        colors[2] = new Color((float) 0, (float) 0, (float) 0.6875);
        colors[3] = new Color((float) 0, (float) 0, (float) 0.7500);
        colors[4] = new Color((float) 0, (float) 0, (float) 0.8125);
        colors[5] = new Color((float) 0, (float) 0, (float) 0.8750);
        colors[6] = new Color((float) 0, (float) 0, (float) 0.9375);
        colors[7] = new Color((float) 0, (float) 0, (float) 1.0000);
        colors[8] = new Color((float) 0, (float) 0.0625, (float) 1.0000);
        colors[9] = new Color((float) 0, (float) 0.1250, (float) 1.0000);
        colors[10] = new Color((float) 0, (float) 0.1875, (float) 1.0000);
        colors[11] = new Color((float) 0, (float) 0.2500, (float) 1.0000);
        colors[12] = new Color((float) 0, (float) 0.3125, (float) 1.0000);
        colors[13] = new Color((float) 0, (float) 0.3750, (float) 1.0000);
        colors[14] = new Color((float) 0, (float) 0.4375, (float) 1.0000);
        colors[15] = new Color((float) 0, (float) 0.5000, (float) 1.0000);
        colors[16] = new Color((float) 0, (float) 0.5625, (float) 1.0000);
        colors[17] = new Color((float) 0, (float) 0.6250, (float) 1.0000);
        colors[18] = new Color((float) 0, (float) 0.6875, (float) 1.0000);
        colors[19] = new Color((float) 0, (float) 0.7500, (float) 1.0000);
        colors[20] = new Color((float) 0, (float) 0.8125, (float) 1.0000);
        colors[21] = new Color((float) 0, (float) 0.8750, (float) 1.0000);
        colors[22] = new Color((float) 0, (float) 0.9375, (float) 1.0000);
        colors[23] = new Color((float) 0, (float) 1.0000, (float) 1.0000);
        colors[24] = new Color((float) 0.0625, (float) 1.0000, (float) 0.9375);
        colors[25] = new Color((float) 0.1250, (float) 1.0000, (float) 0.8750);
        colors[26] = new Color((float) 0.1875, (float) 1.0000, (float) 0.8125);
        colors[27] = new Color((float) 0.2500, (float) 1.0000, (float) 0.7500);
        colors[28] = new Color((float) 0.3125, (float) 1.0000, (float) 0.6875);
        colors[29] = new Color((float) 0.3750, (float) 1.0000, (float) 0.6250);
        colors[30] = new Color((float) 0.4375, (float) 1.0000, (float) 0.5625);
        colors[31] = new Color((float) 0.5000, (float) 1.0000, (float) 0.5000);
        colors[32] = new Color((float) 0.5625, (float) 1.0000, (float) 0.4375);
        colors[33] = new Color((float) 0.6250, (float) 1.0000, (float) 0.3750);
        colors[34] = new Color((float) 0.6875, (float) 1.0000, (float) 0.3125);
        colors[35] = new Color((float) 0.7500, (float) 1.0000, (float) 0.2500);
        colors[36] = new Color((float) 0.8125, (float) 1.0000, (float) 0.1875);
        colors[37] = new Color((float) 0.8750, (float) 1.0000, (float) 0.1250);
        colors[38] = new Color((float) 0.9375, (float) 1.0000, (float) 0.0625);
        colors[39] = new Color((float) 1.0000, (float) 1.0000, (float) 0);
        colors[40] = new Color((float) 1.0000, (float) 0.9375, (float) 0);
        colors[41] = new Color((float) 1.0000, (float) 0.8750, (float) 0);
        colors[42] = new Color((float) 1.0000, (float) 0.8125, (float) 0);
        colors[43] = new Color((float) 1.0000, (float) 0.7500, (float) 0);
        colors[44] = new Color((float) 1.0000, (float) 0.6875, (float) 0);
        colors[45] = new Color((float) 1.0000, (float) 0.6250, (float) 0);
        colors[46] = new Color((float) 1.0000, (float) 0.5625, (float) 0);
        colors[47] = new Color((float) 1.0000, (float) 0.5000, (float) 0);
        colors[48] = new Color((float) 1.0000, (float) 0.4375, (float) 0);
        colors[49] = new Color((float) 1.0000, (float) 0.3750, (float) 0);
        colors[50] = new Color((float) 1.0000, (float) 0.3125, (float) 0);
        colors[51] = new Color((float) 1.0000, (float) 0.2500, (float) 0);
        colors[52] = new Color((float) 1.0000, (float) 0.1875, (float) 0);
        colors[53] = new Color((float) 1.0000, (float) 0.1250, (float) 0);
        colors[54] = new Color((float) 1.0000, (float) 0.0625, (float) 0);
        colors[55] = new Color((float) 1.0000, (float) 0, (float) 0);
        colors[56] = new Color((float) 0.9375, (float) 0, (float) 0);
        colors[57] = new Color((float) 0.8750, (float) 0, (float) 0);
        colors[58] = new Color((float) 0.8125, (float) 0, (float) 0);
        colors[59] = new Color((float) 0.7500, (float) 0, (float) 0);
        colors[60] = new Color((float) 0.6875, (float) 0, (float) 0);
        colors[61] = new Color((float) 0.6250, (float) 0, (float) 0);
        colors[62] = new Color((float) 0.5625, (float) 0, (float) 0);
        colors[63] = new Color((float) 0.5000, (float) 0, (float) 0);

    }

    public Color[] getColors() {
        return colors;
    }
}
