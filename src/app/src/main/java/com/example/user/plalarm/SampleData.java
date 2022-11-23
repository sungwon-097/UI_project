package com.example.user.plalarm;

import java.util.ArrayList;

public class SampleData {
    ArrayList<ListItem> items = new ArrayList<>();

    public ArrayList<ListItem> getItems(){
        ListItem item1 = new ListItem("사인페 수업", "2022-11-11");
        ListItem item2 = new ListItem("사인페 수업123", "2022-12-11");
        ListItem item3 = new ListItem("사인페 수업33333", "2022-10-11");

        items.add(item1);
        items.add(item2);
        items.add(item3);

        return items;
    }
}
