# CassettoFiscale Java API

Progetto che contiene un interceptor da integrare nell'istanza del client OkHttp che gestisce automaticamente l'autenticazione con il backend.

È necessario passare all'interceptor il percorso nel filesystem dove trovare certificato e chiave privata, baseUrl dell'API e institute.

All'interno del progetto è presente una classe Main per fornire un esempio su come utilizzare la libreria.