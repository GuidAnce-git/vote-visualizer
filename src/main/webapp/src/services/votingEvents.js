export function getVotingEvents() {
    return fetch('http://localhost:8080/test/voting')
        .then(data => data.json())
}