import haven.CharWnd.Constipations;
import haven.*;
import haven.ItemInfo.Tip;
import haven.Resource.Image;

import java.awt.image.BufferedImage;

import static haven.L10N.Bundle.*;

class SatiateTip extends Tip {
    Satiate satiate;
    Indir icon;
    double val;

    SatiateTip(Satiate satiate, Owner owner, Indir icon, double val) {
        super(owner);
        this.satiate = satiate;
        this.icon = icon;
        this.val = val;
    }

    public BufferedImage longtip() {
        String satiateStr = L10N.getString(LABEL, "Satiate ");
        BufferedImage sat = Text.render(satiateStr).img;
        int h = sat.getHeight();
        BufferedImage ico = PUtils.convolvedown(((Resource) this.icon.get()).layer(Resource.imgc).img, new Coord(h, h), Constipations.tflt);
        String byStr =L10N.getString(LABEL, " by $col[255,128,128]{%d%%}");
        BufferedImage by = RichText.render(String.format(byStr, (int) Math.round((1.0D - this.val) * 100.0D)), 0, new Object[0]).img;
        return catimgsh(0, sat, ico, by);
    }
}
