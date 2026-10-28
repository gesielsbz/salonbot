const BASE = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080'
const TENANT = import.meta.env.VITE_DEV_TENANT_ID ?? '11111111-1111-1111-1111-111111111111'
export type Service = {id:string;name:string;durationMinutes:number;price:number;active:boolean}
export type Professional = {id:string;name:string;email?:string;active:boolean}
export type Appointment = {id:string;customerId:string;professionalId:string;serviceId:string;startAt:string;endAt:string;status:string}
async function request<T>(path:string, init?:RequestInit):Promise<T>{
  const response=await fetch(`${BASE}${path}`,{...init,headers:{'Content-Type':'application/json','X-Tenant-Id':TENANT,...(init?.headers??{})}})
  if(!response.ok){const body=await response.json().catch(()=>null);throw new Error(body?.message??`HTTP ${response.status}`)}
  return response.json() as Promise<T>
}
export const api={
  services:()=>request<Service[]>('/api/v1/services'),
  professionals:()=>request<Professional[]>('/api/v1/professionals'),
  appointments:(from:string,to:string)=>request<Appointment[]>(`/api/v1/appointments?from=${encodeURIComponent(from)}&to=${encodeURIComponent(to)}`),
  createAppointment:(body:{customerId:string;professionalId:string;serviceId:string;startAt:string})=>request<Appointment>('/api/v1/appointments',{method:'POST',body:JSON.stringify(body)}),
  createService:(body:{name:string;durationMinutes:number;price:number})=>request<Service>('/api/v1/services',{method:'POST',body:JSON.stringify(body)}),
  createProfessional:(body:{name:string;email?:string})=>request<Professional>('/api/v1/professionals',{method:'POST',body:JSON.stringify(body)}),
  createCustomer:(body:{name:string;phone:string})=>request<{id:string}>('/api/v1/customers',{method:'POST',body:JSON.stringify(body)}),
}
