package ee.hitsa.ois.xls;

import java.awt.Color;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.jxls.common.AreaListener;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractColorAreaListener<T extends Object> implements AreaListener, HasWorkbook {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractColorAreaListener.class);
    
    private final String variableName;
    private Workbook workbook;
    
    public AbstractColorAreaListener(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public void beforeApplyAtCell(CellRef cellRef, Context context) {
        
    }

    @Override
    public void afterApplyAtCell(CellRef cellRef, Context context) {
        
    }

    @Override
    public void beforeTransformCell(CellRef srcCell, CellRef targetCell, Context context) {
        
    }

    @Override
    public void afterTransformCell(CellRef srcCell, CellRef targetCell, Context context) {
        if (workbook == null) {
            return;
        }
        try {
            if (styleLocked(srcCell)) {
                return;
            }
            @SuppressWarnings("unchecked") // Should be caught into catch in case if casting went wrong
            T variable = (T) context.getVar(variableName);
            if (variable == null) {
                return;
            }
            Sheet sheet = workbook.getSheet(targetCell.getSheetName());
            if (sheet instanceof XSSFSheet) {
                XSSFRow row = ((XSSFSheet) sheet).getRow(targetCell.getRow());
                if (row != null) {
                    XSSFCell cell = row.getCell(targetCell.getCol());
                    if (cell != null) {
                        XSSFCellStyle newStyle = (XSSFCellStyle) workbook.createCellStyle();
                        newStyle.setDataFormat(cell.getCellStyle().getDataFormat());
                        Color color = getColor(variable);
                        if (!color.equals(Color.WHITE)) {
                            newStyle.setFillForegroundColor(new XSSFColor(getColor(variable)));
                            newStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        }
                        newStyle.setFont(getFont(variable));
                        cell.setCellStyle(newStyle);
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Exception inside of ColorAreaListener: " + e.getMessage());
        }
    }
    
    protected abstract boolean styleLocked(CellRef srcCell);
    
    protected abstract Color getColor(T object);
    
    protected abstract Font getFont(T object);

    @Override
    public Workbook getWorkbook() {
        return workbook;
    }

    @Override
    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }
}
