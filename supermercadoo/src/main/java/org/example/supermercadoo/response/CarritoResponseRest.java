package org.example.supermercadoo.response;

public class CarritoResponseRest extends ResponseRest {
    private CarritoResponse carritoResponse = new CarritoResponse();

    public CarritoResponse getCarritoResponse() {
        return carritoResponse;
    }

    public void setCarritoResponse(CarritoResponse carritoResponse) {
        this.carritoResponse = carritoResponse;
    }
}
