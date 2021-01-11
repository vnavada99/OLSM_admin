package lectures;

public class lecture {
    private String c_id,i_id,date,c_name,i_name;

    public lecture(String c_id, String i_id, String date,String c_name,String i_name) {
        this.c_id = c_id;
        this.i_id = i_id;
        this.date = date;
        this.c_name = c_name;
        this.i_name = i_name;
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

    public String getI_name() {
        return i_name;
    }

    public String getC_name() {
        return c_name;
    }
}
