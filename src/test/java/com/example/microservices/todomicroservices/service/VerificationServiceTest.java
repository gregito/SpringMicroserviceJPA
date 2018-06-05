package com.example.microservices.todomicroservices.service;

import com.example.microservices.todomicroservices.utilities.JwtUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VerificationServiceTest {

    private static final String TEST_EMAIL = "test@email.com";

    private static final String INVALID_JWT = " ";

    private static final String EMAIL_KEY = "email";

    private static final String TEST_JWT = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvdGhlcmVtYWlsQHNvbWVvdGhlcnByb3ZpZGVyL";

    @InjectMocks
    private VerificationService underTest;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private HttpServletRequest request;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testVerifyUserWhenUnableToObtainJwtFromRequestThenFalseReturns() throws UnsupportedEncodingException {
        when(jwtUtils.getJwtFromHttpRequest(request)).thenReturn(Optional.empty());

        assertFalse(underTest.verifyUser(request, TEST_EMAIL));
        verify(jwtUtils, times(1)).getJwtFromHttpRequest(request);
        verify(jwtUtils, times(0)).jwt2Map(any(String.class));
    }

    @Test
    public void testVerifyUserWhenJwtComponentsDoesNotContainsEmailKeyThenFalseReturns() throws UnsupportedEncodingException {
        when(jwtUtils.getJwtFromHttpRequest(request)).thenReturn(Optional.of(INVALID_JWT));
        when(jwtUtils.jwt2Map(INVALID_JWT)).thenReturn(Collections.emptyMap());

        assertFalse(underTest.verifyUser(request, TEST_EMAIL));
        verify(jwtUtils, times(1)).getJwtFromHttpRequest(request);
        verify(jwtUtils, times(1)).jwt2Map(INVALID_JWT);
    }

    @Test
    public void testVerifyUserWhenJwtComponentHasEmailBotDoesNotEqualsToTheProvidedOneThenFalseReturns() throws UnsupportedEncodingException {
        String otherEmail = "something completely different";
        when(jwtUtils.getJwtFromHttpRequest(request)).thenReturn(Optional.of(TEST_JWT));
        when(jwtUtils.jwt2Map(TEST_JWT)).thenReturn(Collections.singletonMap(EMAIL_KEY, otherEmail));

        assertFalse(underTest.verifyUser(request, TEST_EMAIL));
        verify(jwtUtils, times(1)).getJwtFromHttpRequest(request);
        verify(jwtUtils, times(1)).jwt2Map(TEST_JWT);
    }

    @Test
    public void testVerifyUserWhenJwtUtilsThrowsUnsupportedEncodingExceptionWhenTryingToObtainComponentMapThenFalseReturns() throws UnsupportedEncodingException {
        when(jwtUtils.getJwtFromHttpRequest(request)).thenReturn(Optional.of(TEST_JWT));
        when(jwtUtils.jwt2Map(TEST_JWT)).thenThrow(new UnsupportedEncodingException());

        assertFalse(underTest.verifyUser(request, TEST_EMAIL));
        verify(jwtUtils, times(1)).getJwtFromHttpRequest(request);
        verify(jwtUtils, times(1)).jwt2Map(TEST_JWT);
    }

    @Test
    public void testVerifyUserWhenJwtEmailEqualsToTheProvidedOneThenTrueReturns() throws UnsupportedEncodingException {
        when(jwtUtils.getJwtFromHttpRequest(request)).thenReturn(Optional.of(TEST_JWT));
        when(jwtUtils.jwt2Map(TEST_JWT)).thenReturn(Collections.singletonMap(EMAIL_KEY, TEST_EMAIL));

        assertTrue(underTest.verifyUser(request, TEST_EMAIL));
        verify(jwtUtils, times(1)).getJwtFromHttpRequest(request);
        verify(jwtUtils, times(1)).jwt2Map(TEST_JWT);
    }

}