package courses;

public class course {


    private String c_name;
    private String c_level;
    private String c_desc;
    private String c_batch;
    private String c_id;
    private String c_img;

    public course(String c_id,String c_name, String c_level, String c_desc, String c_batch,String c_img) {
        this.c_name = c_name;
        this.c_level = c_level;
        this.c_desc = c_desc;
        this.c_batch = c_batch;
        this.c_img = c_img;
        this.c_id = c_id;
    }
    public String getC_name() {
        return c_name;
    }

    public String getC_level() {
        return c_level;
    }

    public String getC_desc() {
        return c_desc;
    }

    public String getC_batch() {
        return c_batch;
    }
    public String getC_img() {
        return c_img;
    }
    public String getC_id() {
        return c_id;
    }

}

