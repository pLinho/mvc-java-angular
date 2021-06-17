module br.com.nsinova.curso.visao {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens br.com.nsinova.curso.visao to javafx.fxml;
    exports br.com.nsinova.curso.visao;
}
