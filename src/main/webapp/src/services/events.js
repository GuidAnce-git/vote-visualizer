export function getEvents() {
    return fetch('http://localhost:8080/test')
        .then(data => data.json())
}