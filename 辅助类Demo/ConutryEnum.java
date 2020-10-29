package 辅助类Demo;

/**
 * @author phd
 * @version 1.0
 * @date 2020/10/29 14:44
 */
public enum ConutryEnum {

    ONE(1,"齐"),TWO(2,"楚"),THREE(3,"燕"),FOUR(4,"赵"),FIVE(5,"魏"),SIX(6,"韩"),SEVEN(7,"秦");

    private Integer retCode;
    private String retMessage;

    public Integer getRetCode() {
        return retCode;
    }

    public void setRetCode(Integer retCode) {
        this.retCode = retCode;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }

    ConutryEnum(Integer retCode, String retMessage){
        this.retCode = retCode;
        this.retMessage = retMessage;
    }

    public static ConutryEnum forEach_ConutryEnum(int index){
        ConutryEnum[] myArray = ConutryEnum.values();
        for (ConutryEnum element:myArray){
            if(index == element.getRetCode()){
                return element;
            }
        }
        return null;
    }
}
