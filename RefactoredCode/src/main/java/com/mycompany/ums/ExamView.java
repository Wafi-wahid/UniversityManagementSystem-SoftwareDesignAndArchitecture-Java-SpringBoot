package com.mycompany.ums;

import java.util.List;

/**
 * Interface for displaying exam schedules.
 * 
 * - ISP: Interface sirf view/display ke liye methods rakhta hai.
 * - OCP: Naye view implementations bina interface change kiye add ho sakte hain.
 */
public interface ExamView {
    void displayExamSchedule(List<String> courses, ExamScheduleProvider scheduleProvider);
}

/**
 * Default implementation of ExamScheduleView that prints schedule to console.
 * 
 * - SRP: Sirf exam schedule display ka kaam karta hai.
 * - LSP: ExamScheduleView ke jagah seamlessly use ho sakta hai.
 * - DIP: Client abstraction (ExamScheduleView) pe depend karta hai, concrete class pe nahi.
 */
// 