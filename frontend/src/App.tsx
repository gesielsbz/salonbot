import { FormEvent, useEffect, useState } from 'react'
import { api, Appointment, Professional, Service } from './api'
const localInput=(d:Date)=>new Date(d.getTime()-d.getTimezoneOffset()*60000).toISOString().slice(0,16)
export default function App(){
 const [services,setServices]=useState<Service[]>([]),[professionals,setProfessionals]=useState<Professional[]>([]),[appointments,setAppointments]=useState<Appointment[]>([])
 const [message,setMessage]=useState(''),[loading,setLoading]=useState(false),[name,setName]=useState(''),[phone,setPhone]=useState('+5534999999999')
 const [serviceId,setServiceId]=useState(''),[professionalId,setProfessionalId]=useState(''),[startAt,setStartAt]=useState(localInput(new Date(Date.now()+86400000)))
 async function refresh(){
  try{const [s,p,a]=await Promise.all([api.services(),api.professionals(),api.appointments(new Date().toISOString(),new Date(Date.now()+30*86400000).toISOString())]);setServices(s);setProfessionals(p);setAppointments(a);if(!serviceId&&s[0])setServiceId(s[0].id);if(!professionalId&&p[0])setProfessionalId(p[0].id);setMessage('')}
  catch(e){setMessage(e instanceof Error?e.message:'Falha ao carregar')}
 }
 useEffect(()=>{void refresh()},[])
 async function submit(e:FormEvent){e.preventDefault();setLoading(true);setMessage('');try{const c=await api.createCustomer({name,phone});await api.createAppointment({customerId:c.id,professionalId,serviceId,startAt:new Date(startAt).toISOString()});setName('');setMessage('Agendamento criado.');await refresh()}catch(e){setMessage(e instanceof Error?e.message:'Falha ao agendar')}finally{setLoading(false)}}
 async function demoService(){try{await api.createService({name:'Corte',durationMinutes:45,price:60});await refresh();setMessage('Serviço cadastrado.')}catch(e){setMessage(e instanceof Error?e.message:'Falha')}}
 async function demoProfessional(){try{await api.createProfessional({name:'Ana Souza',email:'ana@example.com'});await refresh();setMessage('Profissional cadastrado.')}catch(e){setMessage(e instanceof Error?e.message:'Falha')}}
 return <main className="shell">
  <header className="hero"><div><p className="eyebrow">SALONBOT · DEMONSTRAÇÃO</p><h1>Agenda do salão</h1><p className="muted">Base inicial do painel SaaS multiempresa.</p></div><button className="secondary" onClick={()=>void refresh()}>Atualizar</button></header>
  {message&&<div className="notice" role="status">{message}</div>}
  <section className="stats"><article className="stat"><span>Serviços</span><strong>{services.length}</strong></article><article className="stat"><span>Profissionais</span><strong>{professionals.length}</strong></article><article className="stat"><span>Agendamentos (30 dias)</span><strong>{appointments.length}</strong></article></section>
  <section className="columns">
   <article className="panel"><h2>Novo agendamento</h2><form onSubmit={submit}>
    <label>Nome do cliente<input required value={name} onChange={e=>setName(e.target.value)}/></label>
    <label>WhatsApp E.164<input required value={phone} onChange={e=>setPhone(e.target.value)} pattern="\\+[1-9][0-9]{7,14}"/></label>
    <label>Serviço<select required value={serviceId} onChange={e=>setServiceId(e.target.value)}><option value="">Selecione</option>{services.map(s=><option key={s.id} value={s.id}>{s.name} · {s.durationMinutes} min · R$ {Number(s.price).toFixed(2)}</option>)}</select></label>
    <label>Profissional<select required value={professionalId} onChange={e=>setProfessionalId(e.target.value)}><option value="">Selecione</option>{professionals.map(p=><option key={p.id} value={p.id}>{p.name}</option>)}</select></label>
    <label>Início (fuso do navegador)<input required type="datetime-local" value={startAt} onChange={e=>setStartAt(e.target.value)}/></label>
    <button disabled={loading||!services.length||!professionals.length}>{loading?'Salvando…':'Criar agendamento'}</button>
   </form><p className="hint">O backend calcula a duração e rejeita conflitos. Esta tela ainda não gera slots livres a partir do expediente.</p></article>
   <article className="panel"><h2>Dados de demonstração</h2><p className="muted">Cadastre dados iniciais para testar.</p><div className="button-stack"><button className="secondary" onClick={()=>void demoService()}>Adicionar serviço Corte</button><button className="secondary" onClick={()=>void demoProfessional()}>Adicionar profissional Ana</button></div><h3>Integrações</h3><ul className="integration-list"><li>🟡 WhatsApp <small>Meta ainda não configurada</small></li><li>🟡 Google Calendar <small>OAuth pendente</small></li><li>🟡 n8n <small>Contrato pendente</small></li><li>🟡 Assinaturas <small>Provedor não selecionado</small></li></ul></article>
  </section>
  <section className="panel"><div className="section-heading"><h2>Próximos agendamentos</h2><span className="muted">{appointments.length} registros</span></div>{!appointments.length?<p className="muted">Nenhum agendamento no período.</p>:<div className="table-wrap"><table><thead><tr><th>Início</th><th>Cliente</th><th>Profissional</th><th>Status</th></tr></thead><tbody>{appointments.map(a=><tr key={a.id}><td>{new Date(a.startAt).toLocaleString()}</td><td>{a.customerId.slice(0,8)}…</td><td>{a.professionalId.slice(0,8)}…</td><td><span className="status">{a.status}</span></td></tr>)}</tbody></table></div>}</section>
  <footer>Base de desenvolvimento. Não publicar sem autenticação, autorização e hardening.</footer>
 </main>
}
