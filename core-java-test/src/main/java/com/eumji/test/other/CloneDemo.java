package com.eumji.test.other;

/**
 *
 * 深克隆和浅克隆的demo
 *
 *
 *  要实现clone 必须重写clone方法
 *  且实现Cloneable接口
 *  Cloneable接口是标示
 *  clone方法是object的方法
 *
 *  当如果对象中存在某个对象也是引用类型时如demo中的Card,
 *  如果Card不实现Cloneable接口.则clone的Person对象和原有对象中的 card指向相同的对象
 *
 *  可以自行测试
 *
 *
 *  所以如果想实现深拷贝,就需要将所有的实体都实现Cloneable接口
 *  并调用clone方法.也就是是所有的都指向不同的对象
 *
 *
 * @email eumji025@gmail.com
 * @author: EumJi
 * @date: 18-1-15
 * @time: 下午9:49
 */
public class CloneDemo {

    static class Person implements Cloneable{
        String name;
        String address;
        int age;
        Card card;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Card getCard() {
            return card;
        }

        public void setCard(Card card) {
            this.card = card;
        }

        @Override
        public Person clone() throws CloneNotSupportedException {
            Person person = (Person) super.clone();
            //测试浅拷贝注释如下
            person.setCard(person.getCard().clone());
            return person;
        }
    }

    static class Card implements Cloneable{
        String bankName;
        String bankCard;
        String bankAddress;

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBankCard() {
            return bankCard;
        }

        public void setBankCard(String bankCard) {
            this.bankCard = bankCard;
        }

        public String getBankAddress() {
            return bankAddress;
        }

        public void setBankAddress(String bankAddress) {
            this.bankAddress = bankAddress;
        }

        @Override
        protected Card clone() throws CloneNotSupportedException {

            return (Card) super.clone();
        }
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Card card = new Card();
        Person person = new Person();
        person.setName("hanga");
        person.setAddress("zhangjiajie");
        person.setAge(18);
        person.setCard(card);

        Person person1 = person.clone();
        person1.setName("lisi");
        System.out.println(person1);
    }
}
