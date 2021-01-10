public class lecture {
    private String c_id,i_id,date;

    public lecture(String c_id, String i_id, String date) {
        this.c_id = c_id;
        this.i_id = i_id;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getI_id() {
        return i_id;
    }

    public String getC_id() {
        return c_id;
    }
}
