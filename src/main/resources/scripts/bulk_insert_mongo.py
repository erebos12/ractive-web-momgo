import requests

nbr = 300000
for i in range(100000, nbr + 1):
    doc = {}
    doc["id"] = str(i)
    doc["name"] = "John-" + str(i)
    doc["salary"] = 4000 + i
    try:
        res = requests.post("http://localhost:8080/employees", json=doc)
        if i % 1000 == 0:
            print("Posted " + str(i))
    except Exception as e:
        raise e

print("DONE")
