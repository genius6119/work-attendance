package com.coder520.attend.entity;

import java.util.Date;

public class Attend {
    private Long id;

    private Long userId;

    private Date attendDate;

    private Byte attendWeek;

    private Date attendMoring;

    private Date attendEvening;

    private Integer absence;

    private Byte attendStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getAttendDate() {
        return attendDate;
    }

    public void setAttendDate(Date attendDate) {
        this.attendDate = attendDate;
    }

    public Byte getAttendWeek() {
        return attendWeek;
    }

    public void setAttendWeek(Byte attendWeek) {
        this.attendWeek = attendWeek;
    }

    public Date getAttendMoring() {
        return attendMoring;
    }

    public void setAttendMoring(Date attendMoring) {
        this.attendMoring = attendMoring;
    }

    public Date getAttendEvening() {
        return attendEvening;
    }

    public void setAttendEvening(Date attendEvening) {
        this.attendEvening = attendEvening;
    }

    public Integer getAbsence() {
        return absence;
    }

    public void setAbsence(Integer absence) {
        this.absence = absence;
    }

    public Byte getAttendStatus() {
        return attendStatus;
    }

    public void setAttendStatus(Byte attendStatus) {
        this.attendStatus = attendStatus;
    }
}