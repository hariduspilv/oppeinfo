package ee.hitsa.ois.xls;

import org.jxls.transform.poi.PoiTransformer;

public interface HasPoiTransformer {

    public PoiTransformer getTransformer();
    public void setTransformer(PoiTransformer transformer);
}
