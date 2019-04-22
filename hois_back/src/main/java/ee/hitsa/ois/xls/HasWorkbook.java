package ee.hitsa.ois.xls;

import org.apache.poi.ss.usermodel.Workbook;

public interface HasWorkbook {

    public Workbook getWorkbook();
    public void setWorkbook(Workbook workbook);
}
