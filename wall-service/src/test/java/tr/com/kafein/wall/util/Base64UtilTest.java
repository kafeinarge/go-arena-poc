package tr.com.kafein.wall.util;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static tr.com.kafein.wall.TestConstants.MOCK_STRING;

class Base64UtilTest {

    @Test
    void toBase64_WhenNullFileIsGiven_ThenReturnNull() {
        assertNull(Base64Util.toBase64(null));
    }

    @Test
    void toBase64_WhenEmptyFileIsGiven_ThenReturnNull() {
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(true);
        assertNull(Base64Util.toBase64(mockFile));
    }

    @Test
    void toBase64_WhenValidFileIsGiven_ThenReturnNull() throws IOException {
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(false);
        byte[] bytes = MOCK_STRING.getBytes();

        when(mockFile.getBytes()).thenReturn(bytes);

        MockedStatic<Base64> base64MockedStatic = mockStatic(Base64.class);
        Base64.Encoder mockEncoder = mock(Base64.Encoder.class);
        base64MockedStatic.when(Base64::getEncoder).thenReturn(mockEncoder);
        when(mockEncoder.encodeToString(bytes)).thenReturn(MOCK_STRING);

        String result = Base64Util.toBase64(mockFile);

        assertEquals(MOCK_STRING, result);
        base64MockedStatic.close();
    }
}
