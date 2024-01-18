<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2024-01-16
  Time: 오후 2:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/jsp/cmmn/header.jsp" %>


<div id='calendar'></div>



<script src="/common/fullcalendar-6.1.10/index.global.js"></script>
<script src="/common/fullcalendar-6.1.10/index.global.min.js"></script>
<%-- https://chobopark.tistory.com/245 --%>


<script>
    document.addEventListener('DOMContentLoaded', function() {
        var calendarEl = document.getElementById('calendar');
        var calendar = new FullCalendar.Calendar(calendarEl, {
            // Tool Bar 목록 document : https://fullcalendar.io/docs/toolbar
            headerToolbar: {
                left: 'prevYear,prev,next,nextYear today',
                center: 'title',
                right: 'dayGridMonth,dayGridWeek,dayGridDay'
            },

            selectable: true,
            selectMirror: true,

            navLinks: true, // can click day/week names to navigate views
            editable: true,
            // Create new event
            select: function (arg) {
                Swal.fire({
                    html: "<div class='mb-7'>Create new event?</div><div class='fw-bold mb-5'>Event Name:</div><input type='text' class='form-control' name='event_name' />",
                    icon: "info",
                    showCancelButton: true,
                    buttonsStyling: false,
                    confirmButtonText: "Yes, create it!",
                    cancelButtonText: "No, return",
                    customClass: {
                        confirmButton: "btn btn-primary",
                        cancelButton: "btn btn-active-light"
                    }
                }).then(function (result) {
                    if (result.value) {
                        var title = document.querySelector("input[name=;event_name']").value;
                        if (title) {
                            calendar.addEvent({
                                title: title,
                                start: arg.start,
                                end: arg.end,
                                allDay: arg.allDay
                            })
                        }
                        calendar.unselect()
                    } else if (result.dismiss === "cancel") {
                        Swal.fire({
                            text: "Event creation was declined!.",
                            icon: "error",
                            buttonsStyling: false,
                            confirmButtonText: "Ok, got it!",
                            customClass: {
                                confirmButton: "btn btn-primary",
                            }
                        });
                    }
                });
            },

            // Delete event
            eventClick: function (arg) {
                Swal.fire({
                    text: "Are you sure you want to delete this event?",
                    icon: "warning",
                    showCancelButton: true,
                    buttonsStyling: false,
                    confirmButtonText: "Yes, delete it!",
                    cancelButtonText: "No, return",
                    customClass: {
                        confirmButton: "btn btn-primary",
                        cancelButton: "btn btn-active-light"
                    }
                }).then(function (result) {
                    if (result.value) {
                        arg.event.remove()
                    } else if (result.dismiss === "cancel") {
                        Swal.fire({
                            text: "Event was not deleted!.",
                            icon: "error",
                            buttonsStyling: false,
                            confirmButtonText: "Ok, got it!",
                            customClass: {
                                confirmButton: "btn btn-primary",
                            }
                        });
                    }
                });
            },
            dayMaxEvents: true, // allow "more" link when too many events
            // 이벤트 객체 필드 document : https://fullcalendar.io/docs/event-object
            events: [
                {
                    title: 'All Day Event',
                    start: '2024-01-01'
                },
                {
                    title: 'Long Event',
                    start: '2024-01-07',
                    end: '2024-01-10'
                },
                {
                    groupId: 999,
                    title: 'Repeating Event',
                    start: '2024-01-09T16:00:00'
                },
                {
                    groupId: 999,
                    title: 'Repeating Event',
                    start: '2024-01-16T16:00:00'
                },
                {
                    title: 'Conference',
                    start: '2024-01-11',
                    end: '2024-01-13'
                },
                {
                    title: 'Meeting',
                    start: '2024-01-12T10:30:00',
                    end: '2024-01-12T12:30:00'
                },
                {
                    title: 'Lunch',
                    start: '2024-01-12T12:00:00'
                },
                {
                    title: 'Meeting',
                    start: '2024-01-12T14:30:00'
                },
                {
                    title: 'Happy Hour',
                    start: '2024-01-12T17:30:00'
                },
                {
                    title: 'Dinner',
                    start: '2024-01-12T20:00:00'
                },
                {
                    title: 'Birthday Party',
                    start: '2024-01-13T07:00:00'
                },
                {
                    title: 'Click for Google',
                    url: 'http://google.com/',
                    start: '2024-01-28'
                }
            ]
        });

        calendar.render();
    });

</script>


<%@ include file="/WEB-INF/jsp/cmmn/footer.jsp" %>
<!-- Template Javascript -->
<script src="/common/bootstrap/js/main.js"></script>