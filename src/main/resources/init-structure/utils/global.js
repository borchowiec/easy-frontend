function uuid() {
    const s4 = () => Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
    return `${s4()}${s4()}-${s4()}-${s4()}-${s4()}-${s4()}${s4()}${s4()}`;
}

// modify this key:value object to add more global variables
const global = {
    buildVersion: uuid(),
    buildDate: new Date().toISOString(),
    color: "#333"
}