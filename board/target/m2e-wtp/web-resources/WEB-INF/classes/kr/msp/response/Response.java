package kr.msp.response;

public class Response<H extends ResponseHeader, T> {
    private H header;
    private T body;

    public Response(H header, T body) {
        this.header = header;
        this.body = body;
    }

    public H getHeader() {
        return header;
    }

    public T getBody() {
        return body;
    }
}