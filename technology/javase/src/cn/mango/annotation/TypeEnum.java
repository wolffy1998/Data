package cn.mango.annotation;

/**
 * @Author mango
 * @Since 2020/3/9 17:09
 **/
public enum TypeEnum {


     VARCHAR("varchar"),

     INTEGER("int"),

     DATATIME("datetime");

     private String vale;

    TypeEnum(String value) {
        this.vale = value;
    }

    public String getVale() {
        return vale;
    }

    public void setVale(String vale) {
        this.vale = vale;
    }
}
