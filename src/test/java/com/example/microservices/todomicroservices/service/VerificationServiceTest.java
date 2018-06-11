package com.example.microservices.todomicroservices.service;

import com.example.microservices.todomicroservices.utilities.AuthenticationException;
import com.example.microservices.todomicroservices.utilities.JwtUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VerificationServiceTest {

    private static final String TEST_EMAIL = "test@email.com";

    private static final String INVALID_JWT = " ";

    private static final String EMAIL_KEY = "email";

    private static final String TEST_JWT = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvdGhlcmVtYWlsQHNvbWVvdGhlcnByb3ZpZGVyL";

    private static final String AUTH_EXCEPT_MESSAGE = "Forbidden";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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

        expectedException.expect(AuthenticationException.class);
        expectedException.expectMessage(AUTH_EXCEPT_MESSAGE);

        underTest.verifyUser(request, TEST_EMAIL);

        verify(jwtUtils, times(1)).getJwtFromHttpRequest(request);
        verify(jwtUtils, times(0)).jwt2Map(any(String.class));
    }

    @Test
    public void testVerifyUserWhenJwtComponentsDoesNotContainsEmailKeyThenFalseReturns() throws UnsupportedEncodingException {
        when(jwtUtils.getJwtFromHttpRequest(request)).thenReturn(Optional.of(INVALID_JWT));
        when(jwtUtils.jwt2Map(INVALID_JWT)).thenReturn(Collections.emptyMap());

        expectedException.expect(AuthenticationException.class);
        expectedException.expectMessage(AUTH_EXCEPT_MESSAGE);

        underTest.verifyUser(request, TEST_EMAIL);

        verify(jwtUtils, times(1)).getJwtFromHttpRequest(request);
        verify(jwtUtils, times(1)).jwt2Map(INVALID_JWT);
    }

    @Test
    public void testVerifyUserWhenJwtComponentHasEmailBotDoesNotEqualsToTheProvidedOneThenFalseReturns() throws UnsupportedEncodingException {
        String otherEmail = "something completely different";
        when(jwtUtils.getJwtFromHttpRequest(request)).thenReturn(Optional.of(TEST_JWT));
        when(jwtUtils.jwt2Map(TEST_JWT)).thenReturn(Collections.singletonMap(EMAIL_KEY, otherEmail));

        expectedException.expect(AuthenticationException.class);
        expectedException.expectMessage(AUTH_EXCEPT_MESSAGE);

        underTest.verifyUser(request, TEST_EMAIL);

        verify(jwtUtils, times(1)).getJwtFromHttpRequest(request);
        verify(jwtUtils, times(1)).jwt2Map(TEST_JWT);
    }

    @Test
    public void testVerifyUserWhenJwtUtilsThrowsUnsupportedEncodingExceptionWhenTryingToObtainComponentMapThenFalseReturns() throws UnsupportedEncodingException {
        when(jwtUtils.getJwtFromHttpRequest(request)).thenReturn(Optional.of(TEST_JWT));
        when(jwtUtils.jwt2Map(TEST_JWT)).thenThrow(new UnsupportedEncodingException());

        expectedException.expect(AuthenticationException.class);
        expectedException.expectCause(is(UnsupportedEncodingException.class));
        expectedException.expectMessage(AUTH_EXCEPT_MESSAGE);

        underTest.verifyUser(request, TEST_EMAIL);

        verify(jwtUtils, times(1)).getJwtFromHttpRequest(request);
        verify(jwtUtils, times(1)).jwt2Map(TEST_JWT);
    }

    @Test
    public void testVerifyUserWhenJwtEmailEqualsToTheProvidedOneThenTrueReturns() throws UnsupportedEncodingException {
        when(jwtUtils.getJwtFromHttpRequest(request)).thenReturn(Optional.of(TEST_JWT));
        when(jwtUtils.jwt2Map(TEST_JWT)).thenReturn(Collections.singletonMap(EMAIL_KEY, TEST_EMAIL));

        underTest.verifyUser(request, TEST_EMAIL);

        verify(jwtUtils, times(1)).getJwtFromHttpRequest(request);
        verify(jwtUtils, times(1)).jwt2Map(TEST_JWT);
    }

}