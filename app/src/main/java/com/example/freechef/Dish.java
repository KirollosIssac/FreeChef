package com.example.freechef;

public class Dish {

        private String userid;
        private String Name;
        private String Description;
        private String Price;
        private String Rate;
        private  byte[] Image;

        public Dish(String userid ,String Name,String Description,String Price,String Rate,byte[] Image)
        {
            this.userid = userid;
            this.Name = Name;
            this.Description = Description;
            this.Price = Price;
            this.Rate = Rate;
            this.Image = Image;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String price) {
            Price = price;
        }

        public String getRate() {
            return Rate;
        }

        public void setRate(String rate) {
            Rate = rate;
        }

        public byte[] getImage() {
            return Image;
        }

        public void setImage(byte[] image) {
            Image = image;
        }

        public String getUserid() { return userid;  }

        public void setUserid(String userid) { this.userid = userid; }
}


