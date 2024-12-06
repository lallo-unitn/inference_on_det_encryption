# Secure Cloud Computing: Encrypted Databases and Searchable Symmetric Encryption

## Overview
This repository contains the implementation and report for an assignment focused on two key areas of secure cloud computing: searchable symmetric encryption (SSE) and attacks on property-preserving encryption. The assignment is split into two parts:

1. **Proving Information Leakage of an SSE Scheme**: This part involves the theoretical analysis of information leakage in an SSE scheme, including defining leakage, constructing a simulator, and proving indistinguishability. It quantifies the leakage through a simulation-based framework.

2. **Attacking Property-preserving Encryption**: This part involves a practical attack on an encrypted SQLite database that uses property-preserving encryption techniques. Specifically, it demonstrates vulnerabilities in deterministically encrypted strings and order-preserving encrypted grades.

## Part 2: Attacking Property-preserving Encryption

In this part of the assignment, we explored vulnerabilities in an encrypted SQLite database that contains student records, including first names, last names, and scores. The encryption used follows the CryptDB approach, with deterministic encryption for strings and order-preserving encryption for numeric grades. Our objective was to exploit these encryption schemes using background information to extract meaningful insights about the data.

### Database Setup
The database provided for this task contains fictional student data with encrypted first names, last names, and scores. The strings were encrypted deterministically, while the scores were encrypted using order-preserving encryption, making it possible to infer relationships between the plaintext values and their corresponding ciphertexts.

### Tasks and Results

1. **Number of Unique First Names**
   - We determined that the database contains 100 unique first names. Due to the deterministic encryption, the number of unique encrypted first names matches the number of unique plaintext first names.

2. **Distribution of Last Names**
   - To analyze the distribution of last names, we queried the frequency of each encrypted last name in the database. The resulting distribution revealed that certain names are significantly more common, making them easier to identify using background information. The distribution graph clearly showed the non-uniform nature of the last names, which could be exploited by an attacker to infer plaintext values by constructing a lookup table.

3. **Identifying Students Who Scored 99 Points**
   - Since the grades are encrypted using an order-preserving encryption scheme, we leveraged this property to identify students with specific scores. We sorted the encrypted grades in descending order to find the ciphertext corresponding to a score of 99. After identifying the relevant ciphertext, we queried the database to retrieve the names of the students who scored 99 points. The students identified were:
     - Mario Li
     - Qing Khan
     - Ana Jiang
     - Maria Li

4. **Implementing (α, t)-Secure Index as Mitigation**
   - We implemented an (α, t)-secure index as a mitigation strategy to protect against inference attacks. Specifically, we used (α = 4, t = 0), which groups last names into clusters of 4, thereby adding noise to the search results. This approach confuses an attacker attempting to map distributions by introducing false positives. However, it also leads to less precise search results for legitimate queries, as the client must handle additional noise in the results.

   - The post-mitigation distribution graph showed a significant change, with clusters of 4 replacing the easily mappable distribution observed earlier. This change makes it much harder for an attacker to correlate encrypted values with plaintext values.

### Conclusion
This part of the assignment demonstrated the weaknesses of property-preserving encryption schemes, particularly deterministic and order-preserving encryption. By exploiting background information and the predictable nature of these encryption schemes, we were able to successfully infer details about the encrypted data. The use of (α, t)-secure indexing mitigates some of these vulnerabilities but introduces a trade-off between privacy and search accuracy.

## Leakage Proof for SSE
The report document also contain a leakage proof for a given SSE schema. This is not related to this project.
