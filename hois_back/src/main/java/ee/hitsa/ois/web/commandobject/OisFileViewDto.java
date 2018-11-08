package ee.hitsa.ois.web.commandobject;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.service.OisFileService;
import ee.hitsa.ois.util.EntityUtil;

/**
 * OisFile without fdata, which is supposed to be acquired via OisFileController by id
 */
public class OisFileViewDto {
    private String id;
    private String fname;
    private String ftype;

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
    	this.id = id;
    }
    
    public void setLongId(Long id) {
    	if (id == null) {
			this.id = null;
			return;
		}
		String text = id.toString();
    	byte[] result = null;
    	try {
	        // Cipher
	        Key aesKey = new SecretKeySpec(OisFileService.getKey().getBytes(), "AES");
	        Cipher cipher = Cipher.getInstance("AES");
	        // Encrypt the text
	        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
	        result = cipher.doFinal(text.getBytes());
    	} catch(Exception e) {
            throw new HoisException(e);
        }
    	// Encode encrypted string for url
    	try {
    		this.id = Base64.encodeBase64URLSafeString(result);
    	} catch (Exception e) {
    		throw new HoisException(e);
    	}
    }
    
    public String getFname() {
        return fname;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }
    public String getFtype() {
        return ftype;
    }
    public void setFtype(String ftype) {
        this.ftype = ftype;
    }
    
    public static OisFileViewDto of(OisFile oisFile) {
    	OisFileViewDto dto = EntityUtil.bindToDto(oisFile, new OisFileViewDto(), "id");
    	dto.setLongId(oisFile.getId());
    	return dto;
    }
}
