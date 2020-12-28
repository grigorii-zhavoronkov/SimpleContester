import axios from "axios";

export default axios.create({
    baseURL: 'http://localhost:8080/',
    headers: {
        'Accept' : 'Application/json',
        'Content-Type' : 'Application/json',
        'Access-Control-Allow-Origin': '*'
    }
})
