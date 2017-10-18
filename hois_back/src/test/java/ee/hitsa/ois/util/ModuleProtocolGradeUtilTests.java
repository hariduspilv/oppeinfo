package ee.hitsa.ois.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ee.hitsa.ois.enums.OccupationalGrade;

public class ModuleProtocolGradeUtilTests {
    
    @Test
    public void add() {
        String grade = OccupationalGrade.KUTSEHINDAMINE_3.name();
        List<BigDecimal> proportions = Arrays.asList(BigDecimal.valueOf(0.1), BigDecimal.valueOf(0.8));
        BigDecimal total = BigDecimal.ZERO;
        Assert.assertTrue(ModuleProtocolGradeUtil.add(total, proportions, grade).compareTo(BigDecimal.valueOf(2.7)) == 0);
    }
    
    @Test
    public void positiveNotDistinctiveGrade() {
        Assert.assertTrue(ModuleProtocolGradeUtil.positiveNotDistinctiveGrade(BigDecimal.valueOf(2.5)));
        Assert.assertFalse(ModuleProtocolGradeUtil.positiveNotDistinctiveGrade(BigDecimal.valueOf(2.4)));
    }
    
    @Test
    public void notDistinctiveGrade() {
        Assert.assertTrue(OccupationalGrade.KUTSEHINDAMINE_A.equals(ModuleProtocolGradeUtil.notDistinctiveGrade(BigDecimal.valueOf(2.5))));
        Assert.assertTrue(OccupationalGrade.KUTSEHINDAMINE_MA.equals(ModuleProtocolGradeUtil.notDistinctiveGrade(BigDecimal.valueOf(2.4))));
    }
    
    @Test
    public void getMark() {
        Assert.assertTrue(BigDecimal.valueOf(2)
                .compareTo(ModuleProtocolGradeUtil.getMark(OccupationalGrade.KUTSEHINDAMINE_2.name())) == 0);
    }
    
    @Test
    public void distinctiveGrade2() {
        OccupationalGrade grade = ModuleProtocolGradeUtil.distinctiveGrade(BigDecimal.valueOf(1.5));
        Assert.assertTrue(OccupationalGrade.KUTSEHINDAMINE_2.equals(grade));
    }
    
    @Test
    public void distinctiveGrade3() {
        OccupationalGrade grade = ModuleProtocolGradeUtil.distinctiveGrade(BigDecimal.valueOf(2.5));
        Assert.assertTrue(OccupationalGrade.KUTSEHINDAMINE_3.equals(grade));
        
        grade = ModuleProtocolGradeUtil.distinctiveGrade(BigDecimal.valueOf(3.4));
        Assert.assertTrue(OccupationalGrade.KUTSEHINDAMINE_3.equals(grade));
    }
    
    @Test
    public void distinctiveGrade5() {
        OccupationalGrade grade = ModuleProtocolGradeUtil.distinctiveGrade(BigDecimal.valueOf(4.5));
        Assert.assertTrue(OccupationalGrade.KUTSEHINDAMINE_5.equals(grade));
        
        grade = ModuleProtocolGradeUtil.distinctiveGrade(BigDecimal.valueOf(5.5));
        Assert.assertTrue(OccupationalGrade.KUTSEHINDAMINE_5.equals(grade));
    }
}
