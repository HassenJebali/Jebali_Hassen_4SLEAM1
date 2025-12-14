import { Component, OnInit } from '@angular/core';
import { EnrollmentService, Enrollment } from '../services/enrollment.service';

@Component({
  selector: 'app-enrollment-crud',
  templateUrl: './enrollment-crud.component.html',
  styleUrls: ['./enrollment-crud.component.css']
})
export class EnrollmentCrudComponent implements OnInit {

  enrollments: Enrollment[] = [];
  selectedEnrollment: Enrollment = {};
  errorMessage: string = '';

  constructor(private enrollmentService: EnrollmentService) { }

  ngOnInit(): void {
    this.loadEnrollments();
  }

  // Charger toutes les inscriptions
  loadEnrollments(): void {
    this.enrollmentService.getAllEnrollments().subscribe({
      next: (data) => this.enrollments = data,
      error: (err) => this.errorMessage = err.message
    });
  }

  // Créer une nouvelle inscription
  createEnrollment(): void {
    if (!this.selectedEnrollment.studentName || !this.selectedEnrollment.courseName) return;

    this.enrollmentService.createEnrollment(this.selectedEnrollment).subscribe({
      next: (data) => {
        this.enrollments.push(data);
        this.selectedEnrollment = {};
      },
      error: (err) => this.errorMessage = err.message
    });
  }

  // Mettre à jour une inscription
  updateEnrollment(): void {
    if (!this.selectedEnrollment.idEnrollment) return;

    this.enrollmentService.updateEnrollment(this.selectedEnrollment).subscribe({
      next: () => this.loadEnrollments(),
      error: (err) => this.errorMessage = err.message
    });
  }

  // Supprimer une inscription
  deleteEnrollment(id: number): void {
    this.enrollmentService.deleteEnrollment(id).subscribe({
      next: () => this.loadEnrollments(),
      error: (err) => this.errorMessage = err.message
    });
  }

  // Sélectionner une inscription pour édition
  selectEnrollment(enrollment: Enrollment): void {
    this.selectedEnrollment = { ...enrollment };
  }
}
