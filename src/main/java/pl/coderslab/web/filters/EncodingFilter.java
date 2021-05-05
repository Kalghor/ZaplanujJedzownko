package pl.coderslab.web.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/*")
public class EncodingFilter implements Filter {
    private String charsetEncoding = "utf-8";

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        request.setCharacterEncoding(charsetEncoding);
        response.setCharacterEncoding(charsetEncoding);
        filterChain.doFilter(request, response);
    }
}
