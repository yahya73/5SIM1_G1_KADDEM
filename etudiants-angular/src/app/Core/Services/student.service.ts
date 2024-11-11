import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { departement } from '../Modals/departement';
import { equipe } from '../Modals/equipe';
import { student } from '../Modals/etudiant';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  constructor(private http: HttpClient) { }

  API = 'http://192.168.33.0:8082/kaddem/etudiant';
  public registerStudent(studentData: any) {
    return this.http.post<student>(this.API + '/add-etudiant', studentData);
  }
  public getStudents() {
    return this.http.get(this.API + '/retrieve-all-etudiants');
  }
  public deleteStudent(idEtudiant: any) {
    return this.http.delete(this.API + '/remove-etudiant/'+ idEtudiant);
  }
  public updateStudents(student: any) {
    return this.http.put(this.API + '/update-etudiant', student);
  }

  public assignStudent(idEtudiant:any,idDepart:any){

        return this.http.post<number>(`${this.API}/assignetudianttodepartment/${idEtudiant}/${idDepart}`,{});
  }

   public getDepartements(){

        return this.http.get<departement[]>('http://192.168.33.0:8082/kaddem/DepartmentController/displayalldepartment' );
  }

 public getallequipe() {
    return this.http.get<equipe[]>('http://192.168.33.0:8082/kaddem/EquipeController/displayEquipes' );
  }
  
  public assignstudenttoequipe(idEtudiant:any,ideq:any){


    return this.http.post(`${this.API}/assignEtudiantEquipe/${ideq}/${idEtudiant}`,{});

  }


 
}
