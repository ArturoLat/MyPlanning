package com.example.myplanning.activitats;

import com.example.myplanning.model.Item.Valoracio;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendariUtiles {
    public static LocalDate selectedDate;

    public static ArrayList<LocalDate> diesMesArray(LocalDate date) {
        ArrayList<LocalDate> diesMesArray = new ArrayList<>();
        YearMonth mesAny = YearMonth.from(date);
        int diesMes = mesAny.lengthOfMonth();
        LocalDate primerDiaMes = CalendariUtiles.selectedDate.withDayOfMonth(1);
        int diaSetmana = primerDiaMes.getDayOfWeek().getValue();
        LocalDate prevMonth = selectedDate.minusMonths(1);
        LocalDate nextMonth = selectedDate.plusMonths(1);

        YearMonth prevYearMonth = YearMonth.from(prevMonth);
        int prevDaysInMonth = prevYearMonth.lengthOfMonth();
        diaSetmana -=1;


        for(int i = 1; i <= 42; i++)
        {
            if(i <= diaSetmana)
                diesMesArray.add(LocalDate.of(prevMonth.getYear(),prevMonth.getMonth(), prevDaysInMonth + i - diaSetmana));
            else if(i > diesMes + diaSetmana)
                diesMesArray.add(LocalDate.of(nextMonth.getYear(),nextMonth.getMonth(),i - diaSetmana - diesMes));
            else
                diesMesArray.add(LocalDate.of(selectedDate.getYear(),selectedDate.getMonth(),i - diaSetmana));
        }
        return diesMesArray;
    }

    public static String mesAnyFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public static String anyFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        return date.format(formatter);
    }

    public static String diaMesDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d");
        return date.format(formatter);
    }

    public static ArrayList<Valoracio> llistaValoracions = new ArrayList<>();

    public static Valoracio valoracioPerData(LocalDate data){
        for (Valoracio valoracio:llistaValoracions){
            if(valoracio.getDate().equals(data)){return valoracio;}
        }
        return null;
    }
}
